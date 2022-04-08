# spring-batch-demo

## Checkout
```
git clone https://github.com/chiwoo-samples/spring-batch-demo.git

# for intellij
# idea spring-batch-demo
```

## Build
```
./mvnw clean package -DskipTests=true
```

## Run
```
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloFirstJob
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloSecondJob
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloSequentialJob
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloConditionalJob -processorNo=2
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloFirstJob,helloSecondJob,helloSequentialJob
```

## Use Cases

### Hello First
HelloFirstJob 는 "Hello World!" 문자열을 출력하는 가장 단순한 배치 프로세스 입니다.
- [HelloFirstJob](./src/main/kotlin/example/chiwoo/demo/jobs/hello01/HelloFirstJobConfig.kt) 배치 잡 정의  
- [Hello01JobTest.kt](./src/test/kotlin/example/chiwoo/demo/jobs/Hello01JobTest.kt) 단위 테스트
- 콘솔에서 실행
```
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloFirstJob
```

### Hello Second
HelloSecondJob 는 reader -> processor -> writer 로 역할이 분리된 가장 기본적인 Tasklet 으로 chunk-size 단위로 배치 프로세스를 실행 합니다. 
- [HelloSecondJobConfig.kt](./src/main/kotlin/example/chiwoo/demo/jobs/hello02/HelloSecondJobConfig.kt) 배치 잡 정의
- [Hello02JobTest.kt](./src/test/kotlin/example/chiwoo/demo/jobs/Hello02JobTest.kt) 단위 테스트
- 콘솔에서 실행
```
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloSecondJob
```

### Hello Listener
HelloListenerJobConfig 는 Job, StepExecution, Chunk 에 대해 각각 리스너를 추가 하고 배치 프로세서 실행시 각 리스너의 pre / post 프로세스를 통해 공통 작업을 지원할 수 있습니다.       
- [HelloListenerJobConfig.kt](./src/main/kotlin/example/chiwoo/demo/jobs/hello03/HelloListenerJobConfig.kt) 배치 잡 정의
- [Hello03ListenerJobTest.kt](./src/test/kotlin/example/chiwoo/demo/jobs/Hello03ListenerJobTest.kt) 단위 테스트
- 콘솔에서 실행
```
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloListenerJob
```

### Hello Sequential
HelloSequentialJobConfig 는 step1 -> step2 -> step3 작업 단위 순서로 배치 프로세스를 실행 합니다.  
helloListenerJob 에는 2 개의 step 이 순차적으로 처리 됩니다.
- [HelloSequentialJobConfig.kt](./src/main/kotlin/example/chiwoo/demo/jobs/hello04/HelloSequentialJobConfig.kt) 배치 잡 정의
- [Hello04SequentialJobTest.kt](./src/test/kotlin/example/chiwoo/demo/jobs/Hello04SequentialJobTest.kt) 단위 테스트
- 콘솔에서 실행
```
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloSequentialJob
```

### Hello Conditional
HelloConditionalJobConfig 는 step1 의 작업 처리 결과가 성공인 경우와 실패인 경우에 대해 각각 다른 유형으로 배치 프로세스를 실행 합니다.   
입력 `answer` 값이 홀수 이면 step1 이 `성공`을, 짝수 이면 `실패`를 반환 합니다.  

```
Case1) step1 의 처리 결과가 '성공'인 경우
  step1 -> step2 ->step3 
Case2) step1 의 처리 결과가 '실패'인 경우
  step1 -> step4      
```
helloListenerJob 에는 2 개의 step 이 순차적으로 처리 됩니다.
- [HelloConditionalJobConfig.kt](./src/main/kotlin/example/chiwoo/demo/jobs/hello05/HelloConditionalJobConfig.kt) 배치 잡 정의
- [Hello05ConditionalJobTest.kt](./src/test/kotlin/example/chiwoo/demo/jobs/Hello05ConditionalJobTest.kt) 단위 테스트
- 콘솔에서 실행
```
# 성공 프로세스 실행 예시 
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloConditionalJob -answer=1

# 실패 프로세스 실행 예시  
java -jar target/spring-batch-demo-0.0.1-SNAPSHOT.jar --job.names=helloConditionalJob -answer=2
```

