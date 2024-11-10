# Troubleshooting Guide

## Common Issues and Solutions

### 1. RecordHeaders has been closed.

**Error Message:**
```
[Producer clientId=producer-1] ProducerId set to 3009 with epoch 0
2024-11-04T19:34:56.676+01:00 ERROR 226610 --- [kafka-ws] [nio-8080-exec-1] c.k.w.p.controler.JobOfferController     : RecordHeaders has been closed.

java.lang.IllegalStateException: RecordHeaders has been closed.
	at org.apache.kafka.common.header.internals.RecordHeaders.canWrite(RecordHeaders.java:122) ~[kafka-clients-3.6.2.jar:na]
	at org.apache.kafka.common.header.internals.RecordHeaders.add(RecordHeaders.java:61) ~[kafka-clients-3.6.2.jar:na]
	at org.apache.kafka.common.header.internals.RecordHeaders.add(RecordHeaders.java:68) ~[kafka-clients-3.6.2.jar:na]
	at 
```
**Description:**
This error occurs when attempting to modify or access a `RecordHeaders` object that has already been closed. This typically happens when you try to add headers to a message after the headers have been finalized or closed.

**Solution:**
Ensure that you add all necessary headers before sending the message. Once the headers are closed, they cannot be modified.

**Example:**
```java
// Correct way to add headers
RecordHeaders headers = new RecordHeaders();
headers.add(new RecordHeader("key", "value".getBytes()));

// Send the message with headers
producer.send(new ProducerRecord<>(topic, headers, key, value));
```

### 2. @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Not a managed type: class com.joboffer.ws.core.jpa.entities.JobOffer

---

**Exception: `org.springframework.dao.InvalidDataAccessApiUsageException: Not an entity`**
**Explanation:**  
This exception indicates that Spring cannot recognize a specified class as an entity, usually because the entity is located outside the default package scan path for entities.

**Solution:**  
Ensure that the entity’s package is included in the `@EntityScan` annotation in the main application class, allowing Spring to detect and manage the entity.


---