package com.prfl.libs

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.Document
import java.util.*

class TellonymPersistence(private val mongodbClient: MongoClient = MongoClients.create(), val updatePeriod: Long = 15 * 60 * 1000L){
    private val client: TellonymClient?
    private val db: MongoDatabase = mongodbClient.getDatabase("tellopersist")
    init {
        val collection = db.getCollection("config")
        val options = collection.find().first()
        val token = options?.getString("token")
        client = TellonymClient(token)
        if (token == null){
            if(options != null){
                options.append("token", client.token)
            }else{
                collection.insertOne(Document("token", client.token))
            }
        }

        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                update()
            }
        }
         // Period between successive executions (15 minutes in milliseconds)
        timer.scheduleAtFixedRate(task, 0L, updatePeriod)

    }
    fun getPosts(): List<Tell> {
        val tellsDB = db.getCollection("tells")
        return tellsDB.find().toList().map { tell -> Tell(tell["_id"] as Number, tell["tell"] as String, tell["createdAt"] as Date)}
    }
    fun getPosts(ids: List<Number>): List<Tell>{
        val tellsDB = db.getCollection("tells")
        return tellsDB.find(Document("_id", Document("\$in", ids))).toList().map { tell -> Tell(tell["_id"] as Number, tell["tell"] as String, tell["createdAt"] as Date)}
    }
    private fun update(){
        val tells = client!!.getTells()
        val tellsDB = db.getCollection("tells")
        tells.forEach{tell ->
            if (tellsDB.find(Document("_id", tell.id)).first() == null){
                tellsDB.insertOne(Document().append("_id", tell.id).append("tell", tell.tell).append("createdAt", tell.createdAt))
            }
        }
    }

}