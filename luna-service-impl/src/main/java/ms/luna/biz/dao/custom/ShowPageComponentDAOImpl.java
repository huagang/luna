package ms.luna.biz.dao.custom;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

import ms.luna.biz.dao.custom.model.ShowPageComponent;

@Repository("showPageComponentDAO")
public class ShowPageComponentDAOImpl extends MongoBaseDAO implements ShowPageComponentDAO {
	
	MongoCollection<Document> componentCollection;
	
	@PostConstruct
	public void init() {
		componentCollection = mongoConnector.getDBCollection(COLLECTION_NAME);
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	
	public List<ShowPageComponent> readAllComponent() {
	
		MongoIterable<ShowPageComponent> res = componentCollection.find()
											   .map(new Document2ShowPageComponent());
		List<ShowPageComponent> components = new ArrayList<ShowPageComponent>();
		res.into(components);
		
		return components;
		
	}
	
	public void createComponent(ShowPageComponent component) {
		
		Document document = new Document();
		document.append(FIELD_COMPONENT_NAME, component.getName());
		document.append(FIELD_DISPLAY, component.getDisplay());
		document.append(FIELD_PROPERTIES, component.getProperties());
		
		componentCollection.insertOne(document);		
	}
	
	public void updateComponent(ShowPageComponent component) {
		
		Document document = new Document();
		document.append(FIELD_DISPLAY, component.getDisplay());
		document.append(FIELD_PROPERTIES, component.getProperties());
		
		componentCollection.updateOne(Filters.eq(FIELD_COMPONENT_NAME, component.getName()), document);
	}
	
	public void deleteComponent(String name) {
		
		componentCollection.deleteOne(Filters.eq(FIELD_COMPONENT_NAME, name));
	}
	
	class Document2ShowPageComponent implements Function<Document, ShowPageComponent> {

		@Override
		public ShowPageComponent apply(Document document) {
			// TODO Auto-generated method stub
			ShowPageComponent component = new ShowPageComponent();
			
			component.setName(document.getString(FIELD_COMPONENT_NAME));
			component.setDisplay(document.get(FIELD_DISPLAY, Document.class).toJson());
			component.setProperties(document.get(FIELD_PROPERTIES, Document.class).toJson());
			component.setAction(document.get(FIELD_ACTION, Document.class).toJson());
			
			return component;
		}
		
	}
	
}
