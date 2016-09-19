package ms.luna.biz.dao.custom;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import ms.luna.biz.table.DealTable;
import ms.luna.biz.table.RoomBasicInfoTable;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-09-14
 */
@Repository("dealDAO")
public class DealDAOImpl extends MongoBaseDAO implements DealDAO {

    private final static Logger logger = Logger.getLogger(DealDAOImpl.class);
    MongoCollection<Document> dealCollection;

    @PostConstruct
    public void init() {
        dealCollection = mongoConnector.getDBCollection(DealTable.COLLECTION_NAME);
    }
    @Override
    public void insertDeal(Document document) {
        dealCollection.insertOne(document);
    }

    @Override
    public Document getDeal(String dealId) {
        return dealCollection.find(Filters.eq(DealTable.FIELD_ID, dealId)).first();
    }

    @Override
    public Document getDeal(String dealId, List<String> fields) {
        return dealCollection.find(Filters.eq(DealTable.FIELD_ID, dealId))
                             .projection(Projections.include(fields))
                             .first();
    }

    @Override
    public List<Document> loadDeal(JSONObject jsonQuery) {
        String merchantId = jsonQuery.getString(RoomBasicInfoTable.FIELD_MERCHANT_ID);
        FindIterable<Document> documents = dealCollection.find(Filters.eq(RoomBasicInfoTable.FIELD_MERCHANT_ID, merchantId));
        List<Document> dealList = new ArrayList<>();
        documents.into(dealList);
        return dealList;
    }

    @Override
    public void deleteDeal(String dealId) {
        dealCollection.deleteOne(Filters.eq(DealTable.FIELD_ID, dealId));
    }

    @Override
    public void updateDeal(String dealId, Document document) {
        Document updateDocument = new Document("$set", document);
        dealCollection.updateOne(Filters.eq(DealTable.FIELD_ID, dealId), updateDocument);
    }
}
