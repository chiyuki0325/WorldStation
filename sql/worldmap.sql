create table maps
(
    id                serial,
    title             varchar(72) not null,
    author            varchar(32),
    uploader          integer     not null,
    game_version      integer     not null,
    download_provider integer     not null,
    download_url      varchar(1024)
);

create index maps_author_index
    on maps (author);

create index maps_game_version_index
    on maps (game_version);

create unique index maps_id_uindex
    on maps (id);

create index maps_uploader_index
    on maps (uploader);

alter table maps
    add constraint maps_pk
        primary key (id);
