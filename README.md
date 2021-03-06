# Video app
It is template for adding videos on website. 

## What you need to set up
* Java
* MySql
* Tomcat

## Technologies
* Java
* Angular
* Spring
* SQL

![img.png](img.png)
> Home page

![img_1.png](img_1.png)
> Videos

![img_2.png](img_2.png)
> Contact us

## How to run the app
* Firstly, run the following sql queries: <br/>
```mysql
create table java_letter_list(
    id integer auto_increment primary key,
    message varbinary(400),
    date date,
    senderEmail varbinary(40)
);

create table rozetka_static(
   id int AUTO_INCREMENT PRIMARY key,
   day date not null,
   ips varchar(1000) not null
);

create table rozetka_app_users(
    id int AUTO_INCREMENT PRIMARY key,
    username varchar(50) not null,
    password varchar(400) not null,
    role enum('admin', 'user') default 'user'
);

create table rozetka_app_videos(
    id int AUTO_INCREMENT PRIMARY key,
    path varchar(200) not null,
    title varchar(40) not null,
    created_at datetime not null,
    description varchar(300) not null,
    author_id int not null,
    FOREIGN KEY(author_id) REFERENCES rozetka_app_users(id)
);

create table rozetka_comments(
    id int AUTO_INCREMENT PRIMARY key,
    content varchar(300),
    created_at datetime not null,
    video_id int not null,
    user_id int not null,
    FOREIGN KEY(video_id) REFERENCES rozetka_app_videos(id),
    FOREIGN KEY(user_id) REFERENCES rozetka_app_users(id)
);
```
* Secondly, install java and set up tomcat server.