-- Типы данных
CREATE TYPE "address_level" AS ENUM (
  'RF_SUBJECT',
  'ADMINISTRATIVE_DISTRICT',
  'MUNICIPAL_DISTRICT',
  'URBAN_SETTLEMENT',
  'CITY',
  'LOCALITY',
  'PLANNING_STRUCTURE_ELEMENT',
  'ROAD_NETWORK_ELEMENT',
  'LAND_PLOT',
  'BUILDING',
  'ROOM',
  'ROOM_IN_ROOM',
  'AUTONOMOUS_OKRUG',
  'INTRACITY_LEVEL',
  'ADDITIONAL_TERRITORY',
  'OBJECTS_IN_AT',
  'PARKING_SPACE'
);

CREATE TYPE "role" AS ENUM (
  'INDIVIDUAL',
  'ENTITY',
  'ADMIN'
);

CREATE TYPE "gender" AS ENUM (
  'MALE',
  'FEMALE'
);

CREATE TYPE "payment_frequency" AS ENUM (
  'MONTHLY',
  'QUARTERLY'
);

-- Справочные таблицы
CREATE TABLE "types" (
  "id" BIGSERIAL PRIMARY KEY,
  "text" varchar(100) NOT NULL
);

CREATE TABLE "banks" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar NOT NULL
);

CREATE TABLE "addresses" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar NOT NULL
);

CREATE TABLE "migration_services" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar NOT NULL,
  "address_id" BIGINT NOT NULL
);

-- Таблицы пользователей
CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(20) UNIQUE NOT NULL,
  "email" varchar(100) NOT NULL,
  "email_verified" boolean,
  "password" varchar(100) NOT NULL,
  "phone_number" varchar(30) NOT NULL,
  "role" role NOT NULL
);

CREATE TABLE "individual_user" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "active_passport_id" BIGINT
);

CREATE TABLE "entity_user" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "name" varchar(100) NOT NULL,
  "supervisor_first_name" varchar(20) NOT NULL,
  "supervisor_last_name" varchar(20) NOT NULL,
  "supervisor_surname" varchar(20),
  "address_id" BIGINT NOT NULL,
  "bank_id" BIGINT NOT NULL,
  "checking_account" varchar(40) NOT NULL,
  "itn_number" varchar(30) NOT NULL
);

-- Договоры
CREATE TABLE "agreements" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL, 
  "registration_number" BIGINT NOT NULL,
  "payment_frequency" payment_frequency NOT NULL,
  "additional_conditions" varchar(300) NOT NULL,
  "fine" integer NOT NULL, 
  "starts_from" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "lasts_to" TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- Данные комнат и договоров
CREATE TABLE "room_types" (
  "room_id" BIGINT NOT NULL,
  "type_id" BIGINT NOT NULL
);

CREATE TABLE "rooms" (
  "id" BIGSERIAL PRIMARY KEY,
  "building_id" BIGINT NOT NULL,
  "telephone" boolean NOT NULL, 
  "area" decimal NOT NULL,
  "number" integer NOT NULL,
  "floor" integer NOT NULL,
  "price" integer NOT NULL,
  "fine" integer NOT NULL,
  "description" varchar(300)
);

CREATE TABLE "agreement_room" (
  "id" BIGSERIAL PRIMARY KEY,
  "agreement_id" BIGINT NOT NULL,
  "room_id" BIGINT NOT NULL,
  "purpose_of_rent" varchar(100) NOT NULL,
  "start_of_rent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "end_of_rent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "rent_amount" integer NOT NULL
);

-- Данные о зданиях
CREATE TABLE "buildings" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar NOT NULL,
  "address_id" BIGINT NOT NULL,
  "floor_count" integer NOT NULL,
  "telephone" varchar(30) NOT NULL
);

-- Дополнительные данные
CREATE TABLE "room_images" (
  "id" BIGSERIAL PRIMARY KEY,
  "room_id" BIGINT NOT NULL,
  "url" varchar(60) NOT NULL
);

CREATE TABLE "passports" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "first_name" varchar NOT NULL,
  "last_name" varchar NOT NULL,
  "surname" varchar,
  "date_of_birth" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "date_of_issue" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "migration_service_id" BIGINT NOT NULL,
  "number" varchar NOT NULL,
  "series" varchar NOT NULL, 
  "gender" gender NOT NULL,
  "address_id" BIGINT NOT NULL
);

CREATE TABLE "user_rooms" (
  "user_id" BIGINT NOT NULL,
  "room_id" BIGINT NOT NULL,
  "purpose_of_rent" varchar(100) NOT NULL,
  "start_of_rent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "end_of_rent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  "rent_amount" integer NOT NULL
);

-- Токены
CREATE TABLE "password_reset_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "token"  varchar(100) NOT NULL,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "email_verif_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "token"  varchar(100) NOT NULL,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "refresh_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT NOT NULL,
  "token" varchar UNIQUE NOT NULL,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE "address_parts" (
  "id" BIGSERIAL PRIMARY KEY,
  "object_guid" varchar UNIQUE,
  "name" varchar,
  "type_name" varchar,
  "full_type_name" varchar,
  "level" address_level
);

CREATE TABLE "address_address_parts"(
  "address_id" BIGINT,
  "address_part_id" BIGINT
);

-- Внешние ключи
ALTER TABLE "address_address_parts" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "address_address_parts" ADD FOREIGN KEY ("address_part_id") REFERENCES "address_parts" ("id");

ALTER TABLE "email_verif_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "password_reset_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "refresh_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_rooms" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_rooms" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "room_images" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "passports" ADD FOREIGN KEY ("migration_service_id") REFERENCES "migration_services" ("id");

ALTER TABLE "individual_user" ADD FOREIGN KEY ("active_passport_id") REFERENCES "passports" ("id");

ALTER TABLE "individual_user" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "agreements" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "rooms" ADD FOREIGN KEY ("building_id") REFERENCES "buildings" ("id");

ALTER TABLE "agreement_room" ADD FOREIGN KEY ("agreement_id") REFERENCES "agreements" ("id");

ALTER TABLE "agreement_room" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "passports" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "passports" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "room_types" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "room_types" ADD FOREIGN KEY ("type_id") REFERENCES "types" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("bank_id") REFERENCES "banks" ("id");

ALTER TABLE "migration_services" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "buildings" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

-- Типы, банки, адреса, миграционные службы
-- INSERT INTO "types" ("text") VALUES ('Type1'), ('Type2'), ('Type3');
-- INSERT INTO "banks" ("name") VALUES ('Bank1'), ('Bank2'), ('Bank3');
-- INSERT INTO "addresses" ("name") VALUES ('Address1'), ('Address2'), ('Address3');
-- INSERT INTO "migration_services" ("name", "address_id") VALUES ('Service1', 1), ('Service2', 2), ('Service3', 3);

-- -- Пользователи, здания, типы комнат
-- INSERT INTO "users" ("username", "email", "email_verified", "password", "phone_number", "role") VALUES
-- ('User1', 'user1@email.com', true, 'password1', '1234567890', 'INDIVIDUAL'),
-- ('User2', 'user2@email.com', true, 'password2', '0987654321', 'ENTITY'),
-- ('User3', 'user3@email.com', true, 'password3', '1122334455', 'ADMIN');

-- INSERT INTO "buildings" ("address_id", "floor_count", "telephone") VALUES (1, 5, '123-456-789'), (2, 10, '987-654-321'), (3, 8, '111-222-333');

-- -- Комнаты, пользователи-юридические лица, пользователи-физические лица, паспорта
-- INSERT INTO "rooms" ("building_id", "telephone", "area", "number", "floor", "price", "description") VALUES
-- (1, true, 25.5, 101, 1, 500, 'Description1'),
-- (2, false, 30.0, 201, 2, 700, 'Description2'),
-- (3, true, 40.2, 301, 3, 1000, 'Description3');

-- INSERT INTO "room_types" ("room_id", "type_id") VALUES (1, 1), (2, 2), (3, 3);

-- INSERT INTO "entity_user" ("user_id", "name", "supervisor_first_name", "supervisor_last_name", "supervisor_surname", "address_id", "bank_id", "checking_account", "itn_number") VALUES
-- (2, 'EntityName1', 'SupervisorFirstName1', 'SupervisorLastName1', 'SupervisorSurname1', 1, 1, '12345678901234567890', '123456789012345');

-- INSERT INTO "passports" ("user_id", "first_name", "last_name", "surname", "date_of_birth", "date_of_issue", "migration_service_id", "number", "series", "gender", "address_id") VALUES
-- (1, 'FirstName1', 'LastName1', 'Surname1', '1990-01-01', '2020-01-01', 1, '123456', 'AB', 'MALE', 1),
-- (2, 'FirstName2', 'LastName2', 'Surname2', '1985-05-15', '2019-05-15', 2, '654321', 'CD', 'FEMALE', 2),
-- (3, 'FirstName3', 'LastName3', 'Surname3', '1998-12-30', '2021-12-30', 3, '987654', 'EF', 'MALE', 3);

-- INSERT INTO "individual_user" ("user_id", "active_passport_id") VALUES (1, 1);

-- -- Договоры
-- INSERT INTO "agreements" ("user_id", "registration_number", "payment_frequency", "additional_conditions", "fine", "starts_from", "lasts_to") VALUES
-- (1, 1001, 'MONTHLY', 'Condition1', 50, '2022-01-01', '2023-01-01'),
-- (2, 1002, 'QUARTERLY', 'Condition2', 30, '2022-02-01', '2023-02-01'),
-- (3, 1003, 'MONTHLY', 'Condition3', 40, '2022-03-01', '2023-03-01');

-- -- Пользователи-комнаты, изображения комнат, комнаты-договоры
-- INSERT INTO "user_rooms" ("user_id", "room_id") VALUES (1, 1), (2, 2), (3, 3);

-- INSERT INTO "room_images" ("room_id", "url") VALUES (1, 'image1.jpg'), (2, 'image2.jpg'), (3, 'image3.jpg');

-- INSERT INTO "agreement_room" ("agreement_id", "room_id", "purpose_of_rent", "start_of_rent", "end_of_rent", "rent_amount") VALUES
-- (1, 1, 'Purpose1', '2022-01-01', '2022-12-31', 500),
-- (2, 2, 'Purpose2', '2022-02-01', '2022-05-01', 700),
-- (3, 3, 'Purpose3', '2022-03-01', '2022-06-01', 1000);

-- -- Токены
-- INSERT INTO "email_verif_tokens" ("user_id", "token", "expiry_date") VALUES (1, 'verification_token1', '2022-01-02'), (2, 'verification_token2', '2022-02-02');
-- INSERT INTO "password_reset_tokens" ("user_id", "token", "expiry_date") VALUES (1, 'reset_token1', '2022-01-15'), (2, 'reset_token2', '2022-02-15');
-- INSERT INTO "refresh_tokens" ("user_id", "token", "expiry_date") VALUES (1, 'refresh_token1', '2022-12-31'), (2, 'refresh_token2', '2022-12-31');
