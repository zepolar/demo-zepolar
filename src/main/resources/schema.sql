DROP TABLE IF EXISTS "bank";
CREATE TABLE "public"."bank" (
                                 "id" serial NOT NULL,
                                 "created_at" timestamp,
                                 "updated_at" timestamp,
                                 "active" boolean,
                                 "name" character varying(255),
                                 CONSTRAINT "bank_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

DROP TABLE IF EXISTS "bank_account";
CREATE TABLE "public"."bank_account" (
                                         "id" serial NOT NULL,
                                         "created_at" timestamp,
                                         "updated_at" timestamp,
                                         "number" character varying(255) NOT NULL,
                                         "routing" character varying(255) NOT NULL,
                                         "person_id" bigint,
                                         CONSTRAINT "bank_account_pkey" PRIMARY KEY ("id"),
                                         CONSTRAINT "uk_17vmv222i0bofso3sint89xga" UNIQUE ("number"),
                                         CONSTRAINT "uk_de0h877ct353bm9cmtnq4r5vs" UNIQUE ("routing")
) WITH (oids = false);


DROP TABLE IF EXISTS "flyway_schema_history";
CREATE TABLE "public"."flyway_schema_history" (
                                                  "installed_rank" integer NOT NULL,
                                                  "version" character varying(50),
                                                  "description" character varying(200) NOT NULL,
                                                  "type" character varying(20) NOT NULL,
                                                  "script" character varying(1000) NOT NULL,
                                                  "checksum" integer,
                                                  "installed_by" character varying(100) NOT NULL,
                                                  "installed_on" timestamp DEFAULT now() NOT NULL,
                                                  "execution_time" integer NOT NULL,
                                                  "success" boolean NOT NULL,
                                                  CONSTRAINT "flyway_schema_history_pk" PRIMARY KEY ("installed_rank")
) WITH (oids = false);

CREATE INDEX "flyway_schema_history_s_idx" ON "public"."flyway_schema_history" USING btree ("success");


DROP TABLE IF EXISTS "payment_log";
CREATE TABLE "public"."payment_log" (
                                        "id" SERIAL NOT NULL,
                                        "created_at" timestamp NOT NULL,
                                        "payment" character varying(255) NOT NULL,
                                        CONSTRAINT "payment_log_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "person";
CREATE TABLE "public"."person" (
                                   "id" serial NOT NULL,
                                   "created_at" timestamp,
                                   "updated_at" timestamp,
                                   "name" character varying(255),
                                   "national_identity" character varying(255),
                                   "surname" character varying(255),
                                   CONSTRAINT "person_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "transaction";
CREATE TABLE "public"."transaction" (
                                        "id" serial NOT NULL,
                                        "created_at" timestamp,
                                        "updated_at" timestamp,
                                        "amount" double precision NOT NULL,
                                        "status" character varying(255) NOT NULL,
                                        "type_operation" character varying(255) NOT NULL,
                                        "user_id" integer NOT NULL,
                                        CONSTRAINT "transaction_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "wallet_log";
CREATE TABLE "public"."wallet_log" (
                                       "id" serial NOT NULL,
                                       "created_at" timestamp NOT NULL,
                                       "wallet" character varying(255) NOT NULL,
                                       CONSTRAINT "wallet_log_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


ALTER TABLE ONLY "public"."bank_account" ADD CONSTRAINT "fki60hm2ci2rknaemf8iy1q5s15" FOREIGN KEY (person_id) REFERENCES person(id) NOT DEFERRABLE;

-- 2023-04-04 06:15:37.468114+00
