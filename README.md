# quarkus-jackson-map-format

## Purpose

Reproducer for https://github.com/quarkusio/quarkus/issues/42596 .

## Reproducer

### Scenario 1: Quarkus 3.2

The repo is set up with Quarkus 3.2 to begin with.

Run `quarkus dev` or `./mvnw quarkus:dev` to start the dev server.

In another terminal, use an HTTP client to `POST` a form to `/models` with the form attribute `name=foo`, ex. `http -f :8080/models name=foo`.

This should succeed:

```
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
content-length: 64

{
    "id": 1,
    "labels": [
        {
            "key": "hello",
            "value": "world"
        }
    ],
    "name": "foo"
}
```

### Scenario 1: Quarkus 3.8

Upgrade the reproducer to Quarkus 3.8: `quarkus update -S 3.8`.

Run `quarkus dev` or `./mvnw quarkus:dev` to start the dev server.

In another terminal, use an HTTP client to `POST` a form to `/models` with the form attribute `name=foo`, ex. `http -f :8080/models name=foo`.

This should succeed the same as before, but instead fails:

```
$ http -f :8080/models name=foo
HTTP/1.1 500 Internal Server Error
content-length: 6786
content-type: application/json; charset=utf-8

{
    "details": "Error id 39c8b901-c8eb-4de4-8a4b-2ba3c9d4ff0a-1, java.lang.IllegalArgumentException: Could not deserialize string to java type: JsonJavaType(java.util.Map<java.lang.String, java.lang.String>)",
    "stack": "java.lang.IllegalArgumentException: Could not deserialize string to java type: JsonJavaType(java.util.Map<java.lang.String, java.lang.String>)\n\tat org.hibernate.type.format.jackson.JacksonJsonFormatMapper.fromString(JacksonJsonFormatMapper.java:42)\n\tat org.hibernate.type.descriptor.java.spi.FormatMapperBasedJavaType.fromString(FormatMapperBasedJavaType.java:61)\n\tat org.hibernate.type.descriptor.java.spi.FormatMapperBasedJavaType.deepCopy(FormatMapperBasedJavaType.java:110)\n\tat org.hibernate.type.AbstractStandardBasicType.deepCopy(AbstractStandardBasicType.java:295)\n\tat org.hibernate.type.AbstractStandardBasicType.deepCopy(AbstractStandardBasicType.java:291)\n\tat org.hibernate.type.TypeHelper.deepCopy(TypeHelper.java:52)\n\tat org.hibernate.event.internal.AbstractSaveEventListener.cloneAndSubstituteValues(AbstractSaveEventListener.java:359)\n\tat org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:301)\n\tat org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:219)\n\tat org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:134)\n\tat org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:175)\n\tat org.hibernate.event.internal.DefaultPersistEventListener.persist(DefaultPersistEventListener.java:93)\n\tat org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:77)\n\tat org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:54)\n\tat org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)\n\tat org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:758)\n\tat org.hibernate.internal.SessionImpl.persist(SessionImpl.java:742)\n\tat io.quarkus.hibernate.orm.runtime.session.TransactionScopedSession.persist(TransactionScopedSession.java:145)\n\tat org.hibernate.engine.spi.SessionLazyDelegator.persist(SessionLazyDelegator.java:281)\n\tat org.hibernate.Session_OpdLahisOZ9nWRPXMsEFQmQU03A_Synthetic_ClientProxy.persist(Unknown Source)\n\tat io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.persist(AbstractJpaOperations.java:101)\n\tat io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.persist(AbstractJpaOperations.java:96)\n\tat io.quarkus.hibernate.orm.panache.PanacheEntityBase.persist(PanacheEntityBase.java:53)\n\tat org.acme.ModelsResource.create(ModelsResource.java:33)\n\tat org.acme.ModelsResource_Subclass.create$$superforward(Unknown Source)\n\tat org.acme.ModelsResource_Subclass$$function$$2.apply(Unknown Source)\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:136)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:107)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:38)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:61)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:32)\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(Unknown Source)\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)\n\tat io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)\n\tat org.acme.ModelsResource_Subclass.create(Unknown Source)\n\tat org.acme.ModelsResource$quarkusrestinvoker$create_79c9f3126233a8a5542a248da47e1408f1af01a5.invoke(Unknown Source)\n\tat org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)\n\tat io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:141)\n\tat org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)\n\tat io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:582)\n\tat org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)\n\tat org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)\n\tat org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)\n\tat org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)\n\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\n\tat java.base/java.lang.Thread.run(Thread.java:840)\nCaused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.util.LinkedHashMap<java.lang.String,java.lang.String>` from Array value (token `JsonToken.START_ARRAY`)\n at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 1]\n\tat com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)\n\tat com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1767)\n\tat com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1541)\n\tat com.fasterxml.jackson.databind.deser.std.StdDeserializer._deserializeFromArray(StdDeserializer.java:222)\n\tat com.fasterxml.jackson.databind.deser.std.MapDeserializer.deserialize(MapDeserializer.java:457)\n\tat com.fasterxml.jackson.databind.deser.std.MapDeserializer.deserialize(MapDeserializer.java:32)\n\tat com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:342)\n\tat com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4905)\n\tat com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3848)\n\tat org.hibernate.type.format.jackson.JacksonJsonFormatMapper.fromString(JacksonJsonFormatMapper.java:39)\n\t... 48 more"
}
```

----


```
2024-08-16 11:53:25,849 INFO  [io.qua.dat.dep.dev.DevServicesDatasourceProcessor] (build-17) Dev Services for default datasource (postgresql) started - container ID is df6378e3e223
2024-08-16 11:53:25,850 INFO  [io.qua.hib.orm.dep.dev.HibernateOrmDevServicesProcessor] (build-92) Setting quarkus.hibernate-orm.database.generation=drop-and-create to initialize Dev Services managed database
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2024-08-16 11:53:26,798 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread) SQL Warning Code: 0, SQLState: 00000

2024-08-16 11:53:26,799 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread) table "model" does not exist, skipping
2024-08-16 11:53:26,800 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread) SQL Warning Code: 0, SQLState: 00000
2024-08-16 11:53:26,800 WARN  [org.hib.eng.jdb.spi.SqlExceptionHelper] (JPA Startup Thread) sequence "model_seq" does not exist, skipping
2024-08-16 11:53:26,855 INFO  [io.quarkus] (Quarkus Main Thread) code-with-quarkus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 3.8.5) started in 7.763s. Listening on: http://localhost:8080
2024-08-16 11:53:26,855 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2024-08-16 11:53:26,855 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [agroal, cdi, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, narayana-jta, resteasy-reactive, resteasy-reactive-jackson, smallrye-context-propagation, vertx]
2024-08-16 11:53:28,628 ERROR [io.qua.ver.htt.run.QuarkusErrorHandler] (executor-thread-1) HTTP Request to /models failed, error id: 39c8b901-c8eb-4de4-8a4b-2ba3c9d4ff0a-1: java.lang.IllegalArgumentException: Could not deserialize string to java type: JsonJavaType(java.util.Map<java.lang.String, java.lang.String>)
	at org.hibernate.type.format.jackson.JacksonJsonFormatMapper.fromString(JacksonJsonFormatMapper.java:42)
	at org.hibernate.type.descriptor.java.spi.FormatMapperBasedJavaType.fromString(FormatMapperBasedJavaType.java:61)
	at org.hibernate.type.descriptor.java.spi.FormatMapperBasedJavaType.deepCopy(FormatMapperBasedJavaType.java:110)
	at org.hibernate.type.AbstractStandardBasicType.deepCopy(AbstractStandardBasicType.java:295)
	at org.hibernate.type.AbstractStandardBasicType.deepCopy(AbstractStandardBasicType.java:291)
	at org.hibernate.type.TypeHelper.deepCopy(TypeHelper.java:52)
	at org.hibernate.event.internal.AbstractSaveEventListener.cloneAndSubstituteValues(AbstractSaveEventListener.java:359)
	at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:301)
	at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:219)
	at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:134)
	at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:175)
	at org.hibernate.event.internal.DefaultPersistEventListener.persist(DefaultPersistEventListener.java:93)
	at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:77)
	at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:54)
	at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)
	at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:758)
	at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:742)
	at io.quarkus.hibernate.orm.runtime.session.TransactionScopedSession.persist(TransactionScopedSession.java:145)
	at org.hibernate.engine.spi.SessionLazyDelegator.persist(SessionLazyDelegator.java:281)
	at org.hibernate.Session_OpdLahisOZ9nWRPXMsEFQmQU03A_Synthetic_ClientProxy.persist(Unknown Source)
	at io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.persist(AbstractJpaOperations.java:101)
	at io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.persist(AbstractJpaOperations.java:96)
	at io.quarkus.hibernate.orm.panache.PanacheEntityBase.persist(PanacheEntityBase.java:53)
	at org.acme.ModelsResource.create(ModelsResource.java:33)
	at org.acme.ModelsResource_Subclass.create$$superforward(Unknown Source)
	at org.acme.ModelsResource_Subclass$$function$$2.apply(Unknown Source)
	at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)
	at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:136)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:107)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:38)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:61)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:32)
	at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(Unknown Source)
	at io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)
	at io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)
	at io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)
	at org.acme.ModelsResource_Subclass.create(Unknown Source)
	at org.acme.ModelsResource$quarkusrestinvoker$create_79c9f3126233a8a5542a248da47e1408f1af01a5.invoke(Unknown Source)
	at org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)
	at io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:141)
	at org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)
	at io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:582)
	at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:840)
Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `java.util.LinkedHashMap<java.lang.String,java.lang.String>` from Array value (token `JsonToken.START_ARRAY`)
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 1]
	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)
	at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1767)
	at com.fasterxml.jackson.databind.DeserializationContext.handleUnexpectedToken(DeserializationContext.java:1541)
	at com.fasterxml.jackson.databind.deser.std.StdDeserializer._deserializeFromArray(StdDeserializer.java:222)
	at com.fasterxml.jackson.databind.deser.std.MapDeserializer.deserialize(MapDeserializer.java:457)
	at com.fasterxml.jackson.databind.deser.std.MapDeserializer.deserialize(MapDeserializer.java:32)
	at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:342)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4905)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3848)
	at org.hibernate.type.format.jackson.JacksonJsonFormatMapper.fromString(JacksonJsonFormatMapper.java:39)
	... 48 more
```
