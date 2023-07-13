package com.eshoppingzone.profile.UserProfileService.service;

//@Service
//public class SequenceGeneratorService {
//	private MongoOperations mongoOperations;
//
//	@Autowired
//	public SequenceGeneratorService(MongoOperations mongoOperations) {
//		this.mongoOperations = mongoOperations;
//
//	}
//
//	public int generateSequence(String seqName) {
//		DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
//				new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
//		return !Objects.isNull(counter) ? counter.getSeq() : 1;
//
//	}
//
//}