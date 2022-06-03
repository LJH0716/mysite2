--테이블 삭제
drop table users;

--시퀀스 삭제
drop sequence seq_users_no;

--테이블 생성
create table users(
                     no number,
                     id varchar2(20) unique not null,
                     password varchar2(20) not null,
                     name varchar2(20),
                     gender varchar2(10),
                     primary key(no)
                   );
                   
--시퀀스 생성
create sequence seq_users_id
increment by 1 
start with 1
nocache;

commit;
rollback;

--insert
insert into users
values(seq_users_id.nextval,'jhyi0716','1234','이정화','female');

--select
select  no
        ,id
        ,password
        ,name
        ,gender
from users;

--사용자 1명 정보 가져오기
select  no
        ,id
        ,password
        ,name
        ,gender
from users
where id = 'jhyi0716'
and password = '1234';

--update
update users
set ,password = '3333'
    ,name = '송채현'
    ,gender = 'female'    
where id = 'ddddd';


