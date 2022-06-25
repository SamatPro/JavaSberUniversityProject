package ru.itis.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.itis.models.CustomSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@Slf4j
public class NextSequenceServiceImpl implements NextSequenceService {

    @Autowired
    private MongoOperations mongo;

    @Override
    public Long getNextSequence(String sequenceName) {
        log.debug("Creating next index of sequenceName={}", sequenceName);
        CustomSequence customSequence = mongo.findAndModify(
                query(where("_id").is(sequenceName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                CustomSequence.class
        );
        log.debug("Getting next index of sequenceName={}", sequenceName);

        return customSequence.getSeq();
    }
}
