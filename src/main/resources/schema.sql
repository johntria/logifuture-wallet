CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION pg_database_owner;

CREATE SEQUENCE IF NOT EXISTS public.users_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE SEQUENCE IF NOT EXISTS public.wallet_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;

CREATE TABLE IF NOT EXISTS public.users (
    id int8 NOT NULL,
    first_name varchar(255) NULL,
    last_name varchar(255) NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.wallet (
    balance numeric(38, 2) NULL,
    id int8 NOT NULL,
    user_id int8 NULL,
    state varchar(255) NULL,
    CONSTRAINT wallet_pkey PRIMARY KEY (id),
    CONSTRAINT wallet_state_check CHECK (((state)::text = ANY ((ARRAY['AVAILABLE_BALANCE'::character varying, 'TOTAL_BALANCE'::character varying, 'LOCKED_BALANCE'::character varying])::text[]))),
    CONSTRAINT wallet_user_id_key UNIQUE (user_id),
    CONSTRAINT fkgbusavqq0bdaodex4ee6v0811 FOREIGN KEY (user_id) REFERENCES public.users(id)
    );