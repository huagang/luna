package ms.luna.biz.dao.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.mongodb.client.model.WriteModel;
import org.bson.BsonDateTime;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import org.apache.commons.lang.StringUtils;
import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOneModel;

import edu.emory.mathcs.backport.java.util.Arrays;
import ms.luna.biz.dao.custom.model.MsShowPage;

@Repository("msShowPageDAO")
public class MsShowPageDAOImpl extends MongoBaseDAO implements MsShowPageDAO {

	MongoCollection<Document> showPageCollection;
		
	@PostConstruct
	public void init() {
		showPageCollection = mongoConnector.getDBCollection(COLLECTION_NAME);

	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public List<MsShowPage>	readAllPageDetailByAppId(int appId) {
		
		MongoIterable<MsShowPage> res = showPageCollection.find(Filters.eq(FIELD_APP_ID, appId))
										.sort(new Document(FIELD_PAGE_ORDER, 1))
										.map(new Document2MsShowPage());
		List<MsShowPage> pageList = new ArrayList<MsShowPage>();
		res.into(pageList);
		
		return pageList;
	}
	
	public List<MsShowPage> readIndexPageDetailByAppId(int appId) {
		// index page consists of the first two pages
		Bson query = Filters.and(Filters.eq(FIELD_APP_ID, appId), Filters.lte(FIELD_PAGE_ORDER, 2));
		MongoIterable<MsShowPage> res = showPageCollection.find(query)
										.sort(new Document(FIELD_PAGE_ORDER, 1))
						  				.map(new Document2MsShowPage());
		List<MsShowPage> pageList = new ArrayList<>();
		return res.into(pageList);
		
	}
	
	public List<MsShowPage> readAllPageSummaryByAppId(int appId, List<String> summaryFields) {
		
		MongoIterable<MsShowPage> res = showPageCollection.find(Filters.eq(FIELD_APP_ID, appId))
										.projection(Projections.include(summaryFields))
										.sort(new Document(FIELD_PAGE_ORDER, 1))
										.map(new Document2MsShowPage());
		List<MsShowPage> pageList = new ArrayList<MsShowPage>();
		res.into(pageList);
		
		return pageList;
	}

	public MsShowPage readPageSummary(String pageId, List<String> summaryFields) {
		
		MongoIterable<MsShowPage> res = showPageCollection.find(Filters.eq(FIELD_PAGE_ID, pageId))
										.projection(Projections.include(summaryFields))
										.limit(1)
										.map(new Document2MsShowPage());
		return res.first();
	}
	
	public MsShowPage readPageDetail(String pageId) {
		MongoIterable<MsShowPage> res = showPageCollection.find(Filters.eq(FIELD_PAGE_ID, pageId))
										.limit(1)
										.map(new Document2MsShowPage());
		return res.first();
	}
	
	/**
	 * 
	 * @param page
	 * @return pageId mongodb中的document key(_id)
	 */
	public String createOnePage(MsShowPage page) {
		Document document = insertPage2Document(page);
		showPageCollection.insertOne(insertPage2Document(page));
		return document.getString(FIELD_PAGE_ID);
	}
	
	public void updatePageName(MsShowPage page) {
		Document document = new Document();
		document.append(FIELD_PAGE_NAME, page.getPageName());
		document.append(FIELD_PAGE_CODE, page.getPageCode());
		document.append(FIELD_UPDATE_USER, page.getUpdateUser());
		document.append(FIELD_UPDATE_TIME, new BsonDateTime(System.currentTimeMillis()));
		showPageCollection.updateOne(Filters.eq(FIELD_PAGE_ID, page.getPageId()), new Document("$set", document));
	}

	
	/**
	 * 
	 * @param page should contain all info 
	 * @throws Exception
	 */
	public void updatePage(MsShowPage page) {
		showPageCollection.updateOne(Filters.eq(FIELD_PAGE_ID, page.getPageId()), 
				new Document("$set", updatePage2Document(page)));
	}
	
	public void updatePages(List<MsShowPage> pages) {
		if(pages != null) {
			List<UpdateOneModel<Document>> updates = new ArrayList<>(pages.size());
			for(MsShowPage page : pages) {
				updates.add(new UpdateOneModel<Document>(Filters.eq(FIELD_PAGE_ID, page.getPageId()), 
						new Document("$set", updatePage2Document(page))));
			}
			showPageCollection.bulkWrite(updates);
		}
	}
	
	public void deletePagesByAppId(int appId) {
		showPageCollection.deleteMany(Filters.eq(FIELD_APP_ID, appId));
	}

	public void deletePageById(String pageId) {
		showPageCollection.deleteOne(Filters.eq(FIELD_PAGE_ID, pageId));
	}

	public void copyAllPages(int sourceAppId, int destAppId, String user) {
		List<MsShowPage> msShowPages = readAllPageDetailByAppId(sourceAppId);
		List<Document> documents = new ArrayList<>(msShowPages.size());
		for(MsShowPage msShowPage : msShowPages) {
			msShowPage.setPageId(ObjectId.get().toString());
			msShowPage.setAppId(destAppId);
			msShowPage.setUpdateUser(user);
			documents.add(insertPage2Document(msShowPage));
		}
		showPageCollection.insertMany(documents);
	}
	
	private Document updatePage2Document(MsShowPage page) {
		
		Document document = new Document();
		document.append(FIELD_PAGE_CONTENT, page.getPageContent());
		document.append(FIELD_PAGE_ORDER, page.getPageOrder());
		document.append(FIELD_UPDATE_USER, page.getUpdateUser());
		document.append(FIELD_UPDATE_TIME, new BsonDateTime(System.currentTimeMillis()));
		
		return document;
	}

	private Document insertPage2Document(MsShowPage page) {
		Document document = new Document();
		String pageId = ObjectId.get().toString();
		document.append(FIELD_PAGE_ID, pageId);
		document.append(FIELD_APP_ID, page.getAppId());
		document.append(FIELD_PAGE_NAME, page.getPageName());
		document.append(FIELD_PAGE_CODE, page.getPageCode());
		document.append(FIELD_PAGE_CONTENT, page.getPageContent());
		document.append(FIELD_PAGE_ORDER, page.getPageOrder());
		document.append(FIELD_CREATE_TIME, new BsonDateTime(System.currentTimeMillis()));
		document.append(FIELD_UPDATE_USER, page.getUpdateUser());
		return document;
	}
	
	
	class Document2MsShowPage implements Function<Document, MsShowPage> {
		
		@Override
		public MsShowPage apply(Document document) {
			// TODO Auto-generated method stub
			MsShowPage msShowPage = new MsShowPage();
			msShowPage.setAppId(document.getInteger(FIELD_APP_ID, 0));
			msShowPage.setPageId(document.getString(FIELD_PAGE_ID));
			msShowPage.setPageName(document.getString(FIELD_PAGE_NAME));
			msShowPage.setPageCode(document.getString(FIELD_PAGE_CODE));
			msShowPage.setPageContent(document.get(FIELD_PAGE_CONTENT, Document.class));
			msShowPage.setPageAddr(document.getString(FIELD_PAGE_ADDR));
			msShowPage.setPageOrder(document.getInteger(FIELD_PAGE_ORDER, -1));
			msShowPage.setShareTitle(document.getString(FIELD_SHARE_TITLE));
			msShowPage.setSharePic(document.getString(FIELD_SHARE_PIC));
			msShowPage.setShareDesc(document.getString(FIELD_SHARE_DESC));
			
			return msShowPage;
		}
		
	}


	@Override
	public void updatePageOrder(Map<String, Integer> pageOrders) {
		// TODO Auto-generated method stub
		if(pageOrders != null) {
			List<UpdateOneModel<Document>> updates = new ArrayList<>(pageOrders.size());
			for(Map.Entry<String, Integer> entry : pageOrders.entrySet()) {
				String pageId = entry.getKey();
				int order = entry.getValue();
				Document updateDocument = new Document(FIELD_PAGE_ORDER, order);
				updates.add(new UpdateOneModel<Document>(Filters.eq(FIELD_PAGE_ID, pageId), 
						new Document("$set", updateDocument)));
			}
			showPageCollection.bulkWrite(updates);
		}
		
	}
	
}