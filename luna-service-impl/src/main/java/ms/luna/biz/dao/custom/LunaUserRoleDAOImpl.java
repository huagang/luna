package ms.luna.biz.dao.custom;

import com.mongodb.Function;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import ms.luna.biz.dao.custom.model.LunaUserRole;
import ms.luna.biz.table.LunaUserRoleTable;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * Copyright (C) 2015 - 2016 MICROSCENE Inc., All Rights Reserved.
 *
 * @Author: shawn@visualbusiness.com
 * @Date: 2016-07-21
 */

@Repository("lunaUserRoleDAO")
public class LunaUserRoleDAOImpl extends MongoBaseDAO implements LunaUserRoleDAO {

    private MongoCollection<Document> lunaUserRoleCollection;

    @PostConstruct
    public void init() {
        lunaUserRoleCollection = mongoConnector.getDBCollection(LunaUserRoleTable.COLLECTION_NAME);
    }

    @PreDestroy
    public void destroy() {
    }


    @Override
    public LunaUserRole readUserRoleInfo(String userId) {
        Bson query = Filters.eq(LunaUserRoleTable.FIELD_USER_ID, userId);
        MongoIterable<LunaUserRole> res = lunaUserRoleCollection.find(query)
                                                                .limit(1)
                                                                .map(new Document2UserRole());
        return res.first();
    }

    @Override
    public void createUserRoleInfo(LunaUserRole lunaUserRole) {
        Document document = new Document();
        document.put(LunaUserRoleTable.FIELD_USER_ID, lunaUserRole.getUserId());
        document.put(LunaUserRoleTable.FIELD_ROLE_IDS, lunaUserRole.getRoleIds());
        document.put(LunaUserRoleTable.FIELD_EXTRA, lunaUserRole.getExtra());
        document.put(LunaUserRoleTable.FIELD_UPDATE_TIME, new BsonDateTime(System.currentTimeMillis()));
        lunaUserRoleCollection.insertOne(document);
    }

    @Override
    public void updateUserRoleInfo(LunaUserRole lunaUserRole) {
        Document document = new Document();
        document.put(LunaUserRoleTable.FIELD_ROLE_IDS, lunaUserRole.getRoleIds());
        document.put(LunaUserRoleTable.FIELD_EXTRA, lunaUserRole.getExtra());
        document.put(LunaUserRoleTable.FIELD_UPDATE_TIME, new BsonDateTime(System.currentTimeMillis()));
        Bson query = Filters.eq(LunaUserRoleTable.FIELD_USER_ID, lunaUserRole.getUserId());
        lunaUserRoleCollection.updateOne(query, document);
    }

    @Override
    public void deleteUserRoleInfoByUserId(String userId) {
        Bson query = Filters.eq(LunaUserRoleTable.FIELD_USER_ID, userId);
        lunaUserRoleCollection.deleteOne(query);
    }


    class Document2UserRole implements Function<Document, LunaUserRole> {

        @Override
        public LunaUserRole apply(Document document) {
            LunaUserRole lunaUserRole = new LunaUserRole();
            lunaUserRole.setUserId(document.getString(LunaUserRoleTable.FIELD_USER_ID));
            lunaUserRole.setRoleIds(document.get(LunaUserRoleTable.FIELD_ROLE_IDS, List.class));
            lunaUserRole.setExtra(document.get(LunaUserRoleTable.FIELD_EXTRA, Document.class));
            return lunaUserRole;
        }

    }
}
