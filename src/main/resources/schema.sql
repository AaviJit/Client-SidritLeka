 DROP TABLE IF EXISTS verification_token;
 DROP TABLE IF EXISTS password_reset_token;
 DROP TABLE IF EXISTS user_role;
 DROP TABLE IF EXISTS role_permission;
 DROP TABLE IF EXISTS roles;
 DROP TABLE IF EXISTS permissions;
 DROP TABLE IF EXISTS profile;
 DROP TABLE IF EXISTS users;

CREATE  TABLE users (
	user_id SERIAL,
	user_name VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL ,
	email varchar(100) NOT NULL UNIQUE,
	enabled boolean NOT NULL ,
	PRIMARY KEY (user_id));

CREATE TABLE roles (
	role_id SERIAL,
	role_name varchar(100) NOT NULL UNIQUE,
	role_description varchar(100) NOT NULL,
	PRIMARY KEY (role_id));

CREATE TABLE permissions (
	permission_id SERIAL,
	permission_name varchar(100) NOT NULL UNIQUE,
	permission_description varchar(100) NOT NULL,
	PRIMARY KEY (permission_id));

CREATE TABLE role_permission (
	role_permission_id SERIAL,
	role_id INTEGER,
	permission_id INTEGER,
	FOREIGN KEY (role_id) references roles (role_id),
	FOREIGN KEY (permission_id) references permissions (permission_id),
	PRIMARY KEY (role_permission_id));

CREATE TABLE user_role (
	user_role_id SERIAL,
	user_id INTEGER,
	role_id INTEGER,
	FOREIGN KEY (user_id) references users (user_id),
	FOREIGN KEY (role_id) references roles (role_id),
	PRIMARY KEY (user_role_id));

CREATE TABLE profile (
	profile_id SERIAL,
	user_id INTEGER,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NOT NULL,
	FOREIGN KEY (user_id) references users (user_id),
	PRIMARY KEY (profile_id));

CREATE TABLE password_reset_token (
    reset_token_id SERIAL,
    expiry_date DATE NOT NULL,
    token varchar(255) NOT NULL,
    user_id INTEGER NOT NULL,
	FOREIGN KEY (user_id) references users (user_id),
	PRIMARY KEY (reset_token_id));

CREATE TABLE verification_token (
    verification_id SERIAL,
    expiry_date DATE NOT NULL,
    token varchar(255) NOT NULL,
    user_id INTEGER NOT NULL,
	FOREIGN KEY (user_id) references users (user_id),
	PRIMARY KEY (verification_id));
	
INSERT INTO roles (role_name, role_description) values ('ROLE_USER', 'user');
INSERT INTO roles (role_name, role_description) values ('ROLE_ADMIN', 'admin');
INSERT INTO roles (role_name, role_description) values ('ROLE_MODERATOR', 'moderator');

INSERT INTO permissions (permission_name, permission_description) values ('view_user', 'View user');
INSERT INTO permissions (permission_name, permission_description) values ('update_user', 'Update user');
INSERT INTO permissions (permission_name, permission_description) values ('change_user_role', 'Change users role');
INSERT INTO permissions (permission_name, permission_description) values ('change_user_password', 'Change users password');

INSERT INTO role_permission (role_id, permission_id) values (1, 1);
INSERT INTO role_permission (role_id, permission_id) values (1, 2);
INSERT INTO role_permission (role_id, permission_id) values (2, 1);
INSERT INTO role_permission (role_id, permission_id) values (2, 2);
INSERT INTO role_permission (role_id, permission_id) values (2, 3);
INSERT INTO role_permission (role_id, permission_id) values (2, 4);

-- Password is hashed 'test' just for dummy data and testing purposes
INSERT INTO users (user_name, password, email, enabled) values ('oketh', '$2a$10$DFnKNrvWY/M49BGJTPwr2.gXSGJzDgpLlU7BCcxGzSzGAfTHRYW7u', 'oket2003@gmail.com', true);
INSERT INTO users (user_name, password, email, enabled) values ('user2', '$2a$10$DFnKNrvWY/M49BGJTPwr2.gXSGJzDgpLlU7BCcxGzSzGAfTHRYW7u', 'user2@test.com', true);
INSERT INTO users (user_name, password, email, enabled) values ('admin1', '$2a$10$DFnKNrvWY/M49BGJTPwr2.gXSGJzDgpLlU7BCcxGzSzGAfTHRYW7u', 'admin1@test.com', true);
INSERT INTO users (user_name, password, email, enabled) values ('admin2', '$2a$10$DFnKNrvWY/M49BGJTPwr2.gXSGJzDgpLlU7BCcxGzSzGAfTHRYW7u', 'admin2@test.com', true);

INSERT INTO profile (first_name, last_name, user_id) values ('User1', 'User1', 1);
INSERT INTO profile (first_name, last_name, user_id) values ('User2', 'User2', 2);
INSERT INTO profile (first_name, last_name, user_id) values ('Admin1', 'Admin1', 3);
INSERT INTO profile (first_name, last_name, user_id) values ('Admin2', 'Admin2', 4);

INSERT INTO user_role (user_id, role_id) values (1, 1);
INSERT INTO user_role (user_id, role_id) values (2, 1);
INSERT INTO user_role (user_id, role_id) values (3, 2);
INSERT INTO user_role (user_id, role_id) values (4, 2);