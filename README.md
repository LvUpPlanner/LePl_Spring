## LePl Team Project

-----------------

#### Version

* org.springframework.boot: version '3.1.2'
* io.spring.dependency-management: version '1.1.0'
* java: version 17
* junit: version '4.13.2'

-----------------

#### 1. 요구사항 분석
![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/d0567ab8-1f33-4d64-8711-274eb21c083a)

#### 2. 도메인 모델 분석
![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/a351fe5e-1b13-4752-8914-30d945059f06)

#### 3. 엔티티 설계
![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/4353fc00-6314-4a44-ba04-22818ecfeab2)

#### 4. 테이블 설계
![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/73cd0ba8-b791-4162-bfef-bb1a76853dbf)
------------------
### 11/20
__[Task]__
* remainItme 수정(타이머 사용안할 때): 일정 등록할 때, 계획한 시간만큼 LocalDateTime을 계산해서 remainTime을 초기화함 -> 일정 완료 시, remainTime을 0으로 업데이트 진행

__[캐릭터 화폐]__
* 사용자가 일정을 완료했을 때 경험치를 획득함. 경험치 획득에 따라 캐릭터 화폐도 얻어야 됨. expApi 부분에 캐릭터 화폐 더티체킹 추가! (단, 경험치 하루 최대 획득량과는 무관하게 일정 완료시 화폐는 증가하도록 함)

__[Character_Item]__
* Repository, Service에 addItem() 기능은 save와 중복되는 기능이라서 삭제함.

__[추가 고려 사항]__
* 이미 구매한 아이템의 경우 --> Character_Item 테이블 구조상 다른 캐릭터가 아이템을 구매 했을 때, FK 중복이 일어남... 개선이 필요함!
------------------

### 11/14

__[Item}__
* __Item Domain__: id, type, name, price, purchase_quantity, start_time, end_time
* __Item Repository__: save(), findOne(), findByName(), findAll(), updatePurcharse(), remove()
* __Item Service__: save(), findOne(), findByName(), findAll(), updatePurcharse(), remove()
* __Item ApiController__: "api/v1/item"
    * findItem(): GetMapping, itemId로 아이템 조회, "/find/id/{itemId}"
    * findItemByName(): GetMapping, itemName으로 아이템 조회, "/find/name/{itemName}"
    * findItems(): 아이템 전체 조회, "/all"

__[Character]__
* __Character Domain__: Long money 필드 추가
* __Character Repo/Service__: updateCoint(Long money) 추가, 아이템 구매 시 화폐 차감, CharacterItemApiController에서 사용됨

__[Character_Item]__
* __Character_Item Repo/Service__: updateStatus(Long CharacterId, int status) 추가, 아이템 착용 여부 변경, status == 1 아이템 착용/status == 0 아이템 미착용
* __Character_Item ApiController__: "api/v1/characterItem"
    * buyItem(): PostMapping, 아이템 구매 버튼, "/buy/{itemId}"
    * putItem(): GetMapping, 아이템 착용 버튼. "/put/{itemId}/{status}"

### 06/01
* 연관관계 편의 메서드
* Member 레포지토리, 서비스 개발
* Member Test 진행

### 06/02
* ListsRepository, ListsService 개발
* Lists Test 추가 
* Task에 업무가 추가되면 Lists에 오늘의 일정이 추가되어야 함. 

Ex) Task: 공부하기 추가 --> Lists: 날짜와 해당 날짜의 업무 개수 추가! 

### 06/03
* TaskRepository 개발 
* TaskRepositoryTest 진행 
* 업무 추가시 일정의 count 개수 증가 구현

### 06/05
* 업무 삭제 기능 추가 
* 삭제시 delete query 사용 -> 오류 발생 

![deleteError](https://github.com/LvUpPlanner/LePl_Spring/assets/105353163/cb2f9628-28de-4dbf-a500-f26dc51e9154)

delete 쿼리문은 반환 값이 없음 => createQuery() 의 매개변수중에 Task.class는 반환된 결과를 해당 엔티티 클래스(Task) 형식으로 자동 매핑해주는 역할을 한다. 

하지만 반환 값이 없는 쿼리에 반환 결과 매핑 역할을 해주는 매개변수를 사용해서 오류가 발생했던 것이다. 

Task.class 를 지우고 실행 해보니 원하는 방식으로 test가 진행되었다. 

### 06/06
* TaskService 개발 및 테스트 코드 작성 

### 06/07
* TaskStatusRepository, TaskStatusService 생성

------

### 09/17
* Character 기능 개발 진행중
* 회원가입시 유저의 캐릭터도 생성, 로그인시 유저의 캐릭터 불러오기
* 캐릭터의 레벨도 같이 가져오기

### 09/23
* 캐릭터 생성 테스트 코드 완성하기 -> 예상 시나리오: 캐릭터 생성과 동시에 경험치 0, 레벨 1, 친구(팔로잉) 0으로 세팅
* 친구 기능 -> 팔로우/팔로잉 기능으로 수정하기
* 알림 테이블, 엔티티 만들기

![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/0834a3c2-2b7e-4e14-8d73-a5b73338b471)

### 09/25
* 캐릭터 생성 테스트 완료!
* 친구 기능 -> 팔로우/팔로잉 기능으로 변경 완료!

![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/77f00680-bff2-4d5b-a93d-e5ed9cc61fc8)

-> 테스트 결과 (Rollback)

![image](https://github.com/yujinchoi20/LePl_Team/assets/105353163/5e3ff17c-d250-4ecf-b1c3-14bf156fd5ea)

-> H2 DB

* 영속성 전이(cascade)

@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

@JoinColumn(name = "exp_id") // FK

private Exp exp;

-> FK 영속성 전이 추가!

PK 데이터가 추가되기 전에 FK 데이터가 추가되는 것을 막아줌. 

### 09/26

* Exp 테스트 코드 -> 하루 최대 경험치가 넘어가면 레벨업 안됨, 레벨업 필요 경험치 수식 변경((level - 1) ^ 2) * 2)
* Notification Entity, Repository, Service 추가

###### Service/Character/ExpService.java

-> 매일 경험치를 리셋하는 updatePoint() 메서드 

-> @Scheduled(cron = ), 쿼츠 크론 사용(0 0 0 1/1 * ? *): 매일 오전 12시 마다

* Notification 테스트 코드 추가 예정

### 10/10

* Notification 수정
* MemberApiController 중복회원 추가
* TimerApiContoller 추가 

-> 테스트 코드 작성 예정
