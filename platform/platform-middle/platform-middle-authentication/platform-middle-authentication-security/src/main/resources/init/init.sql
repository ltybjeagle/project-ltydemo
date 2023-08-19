-- 用户表
create table fasp_t_causer_self(
                                   id         number(11),
                                   username   varchar2(32),
                                   password   varchar2(255),
                                   enabled    number(1),
                                   locked     number(1)
);
-- 角色表
create table fasp_t_carole_self(
                                   id       number(11),
                                   name     varchar2(32),
                                   nameZh   varchar2(32)
);
-- 用户角色关系表
create table fasp_t_causerrole_self(
                                       id        number(11),
                                       userid    number(11),
                                       roleid    number(11)
);
-- 资源表
create table fasp_t_pubmenu_self(
                                    id          number(11),
                                    pattern     varchar2(255)
);
-- 角色资源关系表
create table fasp_t_carolemenu_self(
                                       id        number(11),
                                       menuid    number(11),
                                       roleid    number(11)
);

-- 初始化
insert into fasp_t_causer_self(id, username, password, enabled, locked)
values(1, 'root', '$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42', 1, 0);
insert into fasp_t_causer_self(id, username, password, enabled, locked)
values(2, 'admin', '$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42', 1, 0);
insert into fasp_t_causer_self(id, username, password, enabled, locked)
values(3, 'sunny', '$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42', 1, 0);

insert into fasp_t_carole_self(id, name, namezh)
values(1, 'ROLE_DBA', '数据库管理员');
insert into fasp_t_carole_self(id, name, namezh)
values(2, 'ROLE_ADMIN', '系统管理员');
insert into fasp_t_carole_self(id, name, namezh)
values(3, 'ROLE_USER', '用户');

insert into fasp_t_causerrole_self(id, userid, roleid)
values(1, 1, 1);
insert into fasp_t_causerrole_self(id, userid, roleid)
values(2, 1, 2);
insert into fasp_t_causerrole_self(id, userid, roleid)
values(3, 2, 2);
insert into fasp_t_causerrole_self(id, userid, roleid)
values(4, 3, 3);

insert into fasp_t_pubmenu_self(id, pattern)
values(1, '/db/**');
insert into fasp_t_pubmenu_self(id, pattern)
values(2, '/admin/**');
insert into fasp_t_pubmenu_self(id, pattern)
values(3, '/user/**');

insert into fasp_t_carolemenu_self(id, menuid, roleid)
values(1, 1, 1);
insert into fasp_t_carolemenu_self(id, menuid, roleid)
values(2, 2, 2);
insert into fasp_t_carolemenu_self(id, menuid, roleid)
values(3, 3, 3);