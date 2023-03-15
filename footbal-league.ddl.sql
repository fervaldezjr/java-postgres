
create table leagues (
    league_id int,
    name      text not null,

    primary key league_id,
) 

create table teams (
    team_id   int,
    league_id int,
    name      text not null,

    primary key team_id,
    foreign key (league_id) references league (league_id),
)
