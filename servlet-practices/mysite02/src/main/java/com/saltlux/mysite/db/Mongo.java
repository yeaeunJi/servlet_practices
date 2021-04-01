package com.saltlux.mysite.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Mongo {
	private static Mongo mongo ;
	private static MongoDatabase db = null;

	static {
		mongo =  new Mongo();
	} // static initializer(자바 프로그램 실행 시 단한번 호출됨)

	private Mongo(){	
		System.out.println("***************** Mongo class 생성자 *****************");
		normalConnect();
	} ; // Constructor

	public void normalConnect(){
		MongoClient mongo = new MongoClient("localhost", 27017);
		System.out.println("***************** Mongo 접속 완료 *****************");

		db = mongo.getDatabase("webdb");

		// insert basic 
		//			MongoCollection<Document> boards = db.getCollection("board");
		//
		//			Document doc = new Document();
		//			doc.append("title", "제목입니다3.");
		//			doc.append("contents", "내용입니다3");
		//			doc.append("regDate", "20210331 17:11");
		//			boards.insertOne(doc);

		// select 
//		MongoCollection<Document> result = db.getCollection("board");			
//		try (MongoCursor<Document> cur = result.find().iterator()) {
//			while (cur.hasNext()) {
//
//				Document doc1 = cur.next();
//				//System.out.println(doc1.values());
//				List cars = new ArrayList(doc1.values());
//				// String JsonResult = document.toJson();
//				System.out.printf("%s: %s%n", cars.get(1), cars.get(2));
//			}
//		}
		
		// mongo : update 
		// UpdateResult = collection.updateOne/updateMany(필터, 수정할 내용을 담은 dom 객체)
		// db.blog[테이블 명].find() : 내용 확인
		// db.테이블 명.remove({“name” : “book01”}) : 특정한 Documents를 지우는 법
		//collection.insert(doc);
		// db.getCollection()을 통해 컬렉션을 가져올 수 있음
		System.out.println("***************** normal Mongo connection 완료 *****************");
	}


	public static Mongo getInstance() {
		return mongo;
	}

	// db connection method
	public static MongoDatabase getConnection(){
		return db;
	}
}
