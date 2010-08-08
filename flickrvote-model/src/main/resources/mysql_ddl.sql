
    create table challenge (
        tag varchar(50) not null,
        notes longtext,
        end_date datetime,
        description varchar(255) not null,
        start_date datetime,
        version bigint,
        voting_open_date datetime,
        photographer_flickr_id varchar(50),
        primary key (tag)
    ) ENGINE=InnoDB;

    create table image (
        flickr_id varchar(50) not null,
        final_rank bigint not null,
        final_vote_count bigint not null,
        large_image varchar(255),
        medium_image varchar(255),
        page varchar(255),
        posted_date datetime,
        title varchar(100),
        version bigint,
        challenge_tag varchar(50),
        photographer_flickr_id varchar(50),
        primary key (flickr_id)
    ) ENGINE=InnoDB;

    create table photographer (
        flickr_id varchar(50) not null,
        administrator bit,
        fullname varchar(100),
        icon_url varchar(255),
        twitter varchar(15),
        username varchar(50),
        version bigint,
        primary key (flickr_id)
    ) ENGINE=InnoDB;

    create table votes (
        id bigint not null auto_increment,
        version bigint,
        image_flickr_id varchar(50),
        photographer_flickr_id varchar(50),
        primary key (id),
        unique (image_flickr_id, photographer_flickr_id)
    ) ENGINE=InnoDB;

    alter table challenge 
        add index FK539A7C63FBF9A4DC (photographer_flickr_id), 
        add constraint FK539A7C63FBF9A4DC 
        foreign key (photographer_flickr_id) 
        references photographer (flickr_id);

    alter table image 
        add index FK5FAA95BFBF9A4DC (photographer_flickr_id), 
        add constraint FK5FAA95BFBF9A4DC 
        foreign key (photographer_flickr_id) 
        references photographer (flickr_id);

    alter table image 
        add index FK5FAA95BFA7332EB (challenge_tag), 
        add constraint FK5FAA95BFA7332EB 
        foreign key (challenge_tag) 
        references challenge (tag);

    alter table votes 
        add index FK6B30AC9FBF9A4DC (photographer_flickr_id), 
        add constraint FK6B30AC9FBF9A4DC 
        foreign key (photographer_flickr_id) 
        references photographer (flickr_id);

    alter table votes 
        add index FK6B30AC9F91713D4 (image_flickr_id), 
        add constraint FK6B30AC9F91713D4 
        foreign key (image_flickr_id) 
        references image (flickr_id);
