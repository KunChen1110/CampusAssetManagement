create table if not exists asset_category (
    id bigint primary key auto_increment,
    name varchar(64) not null,
    type int null comment 'Category type defined by campus business',
    description varchar(255) null,
    status int not null default 1 comment '0 disabled, 1 enabled',
    create_time datetime not null,
    update_time datetime not null,
    unique key uk_asset_category_name (name)
) engine=InnoDB default charset=utf8mb4;

create table if not exists asset (
    id bigint primary key auto_increment,
    asset_code varchar(64) not null,
    name varchar(128) not null,
    category_id bigint not null,
    location varchar(128) null,
    owner_department varchar(128) null,
    status varchar(32) not null default 'IDLE' comment 'ACTIVE, IDLE, MAINTENANCE, RETIRED',
    purchase_date date null,
    original_value decimal(12, 2) not null default 0.00,
    create_time datetime not null,
    update_time datetime not null,
    unique key uk_asset_code (asset_code),
    key idx_asset_category (category_id),
    key idx_asset_status (status),
    key idx_asset_location (location),
    constraint fk_asset_category foreign key (category_id) references asset_category(id)
) engine=InnoDB default charset=utf8mb4;

create table if not exists asset_usage_record (
    id bigint primary key auto_increment,
    asset_id bigint not null,
    user_name varchar(64) not null,
    department varchar(128) null,
    usage_start_time datetime not null,
    usage_end_time datetime null,
    purpose varchar(255) null,
    status varchar(32) not null default 'IN_USE' comment 'IN_USE, RETURNED, OVERDUE',
    create_time datetime not null,
    update_time datetime not null,
    key idx_usage_asset (asset_id),
    key idx_usage_department (department),
    key idx_usage_start_time (usage_start_time),
    constraint fk_asset_usage_asset foreign key (asset_id) references asset(id)
) engine=InnoDB default charset=utf8mb4;

create table if not exists asset_operation_log (
    id bigint primary key auto_increment,
    asset_id bigint not null,
    operation_type varchar(32) not null comment 'CREATE, UPDATE, DELETE, STATUS_CHANGE',
    old_status varchar(32) null,
    new_status varchar(32) null,
    remark varchar(255) null,
    operator_id bigint null,
    create_time datetime not null,
    key idx_asset_log_asset (asset_id),
    key idx_asset_log_operation (operation_type),
    key idx_asset_log_create_time (create_time)
) engine=InnoDB default charset=utf8mb4;

insert into asset_category(name, type, description, status, create_time, update_time)
values
('Teaching Equipment', 1, 'Classroom and laboratory teaching assets', 1, now(), now()),
('Office Equipment', 2, 'Office and administration assets', 1, now(), now())
on duplicate key update update_time = values(update_time);
