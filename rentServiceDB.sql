CREATE TYPE "room_type" AS ENUM (
  'REGULAR',
  'IMPROVED',
  'EU-RENOVATED'
);

CREATE TYPE "payment_frequency" AS ENUM (
  'MONTHLY',
  'QUARTERLY'
);

CREATE TYPE "role" AS ENUM (
  'INDIVIDUAL',
  'ENTITY',
  'MODERATOR',
  'ADMIN'
);

CREATE TYPE "gender" AS ENUM (
  'MALE',
  'FEMALE'
);

CREATE TABLE "users" (
  "id" BIGSERIAL PRIMARY KEY,
  "username" varchar(20) UNIQUE,
  "email" varchar(100),
  "email_verified" boolean,
  "password" varchar(100),
  "phone_number" varchar(30),
  "role" role
);

CREATE TABLE "individual_user" (
  "user_id" BIGINT,
  "passport_id" BIGINT
);

CREATE TABLE "entity_user" (
  "user_id" BIGINT,
  "name" varchar(100),
  "supervisor_full_name" varchar(100),
  "address" varchar(100),
  "bank_name" varchar(30),
  "checking_account" varchar(40),
  "itn_number" varchar(30)
);

CREATE TABLE "agreements" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT, 
  "registration_number" BIGINT,
  "payment_frequency" payment_frequency,
  "fine" integer, 
  "starts_from" TIMESTAMP WITHOUT TIME ZONE,
  "lasts_to" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "rooms" (
  "id" BIGSERIAL PRIMARY KEY,
  "building_id" BIGINT,
  "telephone" boolean, 
  "area" decimal,
  "number" integer,
  "floor" integer,
  "type" room_type
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
  "district" varchar(30),
  "address" varchar(30),
  "floor_count" integer,
  "telephone" varchar(30)
);

CREATE TABLE "passports" (
  "id" BIGSERIAL PRIMARY KEY,
  "user_id" BIGINT, 
  "fullname" varchar(60),
  "date_of_birth" TIMESTAMP WITHOUT TIME ZONE,
  "date_of_issue" TIMESTAMP WITHOUT TIME ZONE,
  "issued_by" varchar(100),
  "number" integer,
  "series" integer,
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

ALTER TABLE "refresh_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "email_verif_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "password_reset_tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "individual_user" ADD FOREIGN KEY ("passport_id") REFERENCES "passports" ("id");

ALTER TABLE "individual_user" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "entity_user" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "agreements" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "rooms" ADD FOREIGN KEY ("building_id") REFERENCES "buildings" ("id");

ALTER TABLE "agreement_room" ADD FOREIGN KEY ("agreement_id") REFERENCES "agreements" ("id");

ALTER TABLE "agreement_room" ADD FOREIGN KEY ("room_id") REFERENCES "rooms" ("id");

ALTER TABLE "passports" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");