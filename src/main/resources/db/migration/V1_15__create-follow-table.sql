use vibely_social_webapp;
create table follow (
    id bigint auto_increment primary key ,
    user_id bigint,
    followed_user_id bigint
)