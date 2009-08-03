CREATE TABLE challenge (
tag varchar(50) NOT NULL,
end_date datetime DEFAULT NULL,
description varchar(255) NOT NULL,
start_date datetime DEFAULT NULL,
voting_open_date datetime DEFAULT NULL,
version bigint(20) DEFAULT NULL,
PRIMARY KEY (tag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE photographer (
flickr_id varchar(50) NOT NULL,
administrator bit(1) DEFAULT NULL,
fullname varchar(100) DEFAULT NULL,
token varchar(100) DEFAULT NULL,
username varchar(50) DEFAULT NULL,
twitter varchar(15) DEFAULT NULL,
version bigint(20) DEFAULT NULL,
PRIMARY KEY (flickr_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE image (
flickr_id varchar(50) NOT NULL,
medium_image varchar(255) DEFAULT NULL,
page varchar(255) DEFAULT NULL,
title varchar(100) DEFAULT NULL,
photographer_flickr_id varchar(50) DEFAULT NULL,
version bigint(20) DEFAULT NULL,
challenge_tag varchar(50) DEFAULT NULL,
final_vote_count bigint(20) NOT NULL DEFAULT '0',
final_rank bigint(20) NOT NULL DEFAULT '0',
posted_date datetime DEFAULT NULL,
PRIMARY KEY (flickr_id),
KEY FK5FAA95BFBF9A4DC (photographer_flickr_id),
KEY FK5FAA95BFA7332EB (challenge_tag),
CONSTRAINT FK5FAA95BFA7332EB FOREIGN KEY (challenge_tag) REFERENCES challenge (tag),
CONSTRAINT FK5FAA95BFBF9A4DC FOREIGN KEY (photographer_flickr_id) REFERENCES photographer (flickr_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE votes (
id bigint(20) NOT NULL AUTO_INCREMENT,
version bigint(20) DEFAULT NULL,
photographer_flickr_id varchar(50) DEFAULT NULL,
image_flickr_id varchar(50) DEFAULT NULL,
PRIMARY KEY (id),
KEY FK6B30AC9FBF9A4DC (photographer_flickr_id),
KEY FK6B30AC9F91713D4 (image_flickr_id),
CONSTRAINT FK6B30AC9F91713D4 FOREIGN KEY (image_flickr_id) REFERENCES image (flickr_id),
CONSTRAINT FK6B30AC9FBF9A4DC FOREIGN KEY (photographer_flickr_id) REFERENCES photographer (flickr_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;