CREATE TYPE "payment_frequency" AS ENUM (
  'MONTHLY',
  'QUARTERLY'
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
  "user_id" BIGINT,
  "active_passport_id" BIGINT
);

CREATE TABLE "entity_user" (
  "user_id" BIGINT,
  "name" varchar(100),
  "supervisor_first_name" varchar(20),
  "supervisor_last_name" varchar(20),
  "supervisor_surname" varchar(20),
  "address_id" BIGINT,
  "bank_id" BIGINT,
  "checking_account" varchar(40),
  "itn_number" varchar(30)
);

CREATE TABLE "agreements" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT, 
  "registration_number" BIGINT,
  "payment_frequency" payment_frequency,
  "additional_conditions" varchar(300),
  "fine" integer, 
  "starts_from" TIMESTAMP WITHOUT TIME ZONE,
  "lasts_to" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "types" (
  "id" BIGSERIAL PRIMARY KEY,
  "text" varchar(100)
);

CREATE TABLE "room_types" (
  "room_id" BIGINT,
  "type_id" BIGINT
);

CREATE TABLE "user_rooms" (
  "user_id" BIGINT,
  "room_id" BIGINT
);

CREATE TABLE "room_images" (
  "id" BIGSERIAL PRIMARY KEY,
  "room_id" BIGINT,
  "url" varchar(60)
);

CREATE TABLE "rooms" (
  "id" BIGSERIAL PRIMARY KEY,
  "building_id" BIGINT,
  "telephone" boolean, 
  "area" decimal,
  "number" integer,
  "floor" integer,
  "price" integer,
  "description" varchar(300)
);

CREATE TABLE "agreement_room" (
  "id" BIGSERIAL PRIMARY KEY,
  "agreement_id" BIGINT,
  "room_id" BIGINT,
  "purpose_of_rent" varchar(100),
  "start_of_rent" TIMESTAMP WITHOUT TIME ZONE,
  "end_of_rent" TIMESTAMP WITHOUT TIME ZONE,
  "rent_amount" integer
);

CREATE TABLE "buildings" (
  "id" BIGSERIAL PRIMARY KEY,
  "address_id" BIGINT,
  "floor_count" integer,
  "telephone" varchar(30)
);

CREATE TABLE "migration_services" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar,
  "address_id" BIGINT
);

CREATE TABLE "passports" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "first_name" varchar,
  "last_name" varchar,
  "surname" varchar,
  "date_of_birth" TIMESTAMP WITHOUT TIME ZONE,
  "date_of_issue" TIMESTAMP WITHOUT TIME ZONE,
  "migration_service_id" BIGINT,
  "number" varchar,
  "series" varchar, 
  "gender" gender,
  "place_of_birth" varchar(100)
);

CREATE TABLE "password_reset_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "email_verif_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token"  varchar(100),
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "refresh_tokens" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT,
  "token" varchar UNIQUE,
  "expiry_date" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "banks" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "addresses" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar,
  "fias_id" varchar
);

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

ALTER TABLE "room_types" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "room_types" ADD FOREIGN KEY ("type_id") REFERENCES "types" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("bank_id") REFERENCES "banks" ("id");

ALTER TABLE "migration_services" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "buildings" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("address_id") REFERENCES "addresses" ("id");