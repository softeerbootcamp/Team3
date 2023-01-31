create table users(
	id int auto_increment PRIMARY KEY,
	userId varchar(64) unique not null,
	userName varchar(16) null,
	password char(60) not null,
	email varchar(64) unique not null 
);

create table articles(
	id int auto_increment PRIMARY KEY,
    hostId int not null,
    title varchar(20) not null,
    image blob null,
    curr smallint not null default 1,
    total smallint not null,
    isClosed boolean default false,
    content TEXT null,
    meetingDay date not null,
    regionId int not null,
    categoryId int not null,
    foreign key (hostId) references users (id),
    foreign key (regionId) references regions (id),
    foreign key (categoryId) references categories (id)
);

create table comments(
	id int auto_increment primary key,
    articleId int not null,
    userId int not null,
    date date not null,
    content varchar(100) null,
    foreign key (articleId) references articles (id),
    foreign key (userId) references users (id)
);

create table user_article_relationship(
	userId int not null,
    articleId int not null,
    foreign key (userId) references users (id),
    foreign key (articleId) references articles (id)
);

create table regions(
	id int primary key,
    regionName tinytext not null
);

create table categories(
	id int primary key,
    title tinytext not null
)

