-- PostgreSQL database dump

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


-- Name: id-mercadoria; Type: SEQUENCE; Schema: public; Owner: ti2cc


CREATE SEQUENCE public."id-mercadoria"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE public."id-mercadoria" OWNER TO ti2cc;

SET default_tablespace = '';

SET default_table_access_method = heap;


-- Name: mercadoria; Type: TABLE; Schema: public; Owner: ti2cc


CREATE TABLE public.mercadoria (
    id integer DEFAULT nextval('public."id-mercadoria"'::regclass) NOT NULL,
    descricao text,
    valor double precision,
    quantidade integer,
    dataproducao timestamp without time zone,
    validade date
);


ALTER TABLE public.mercadoria OWNER TO ti2cc;


-- Name: mercadoria mercadoria_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc


ALTER TABLE ONLY public.mercadoria
    ADD CONSTRAINT mercadoria_pkey PRIMARY KEY (id);



-- PostgreSQL database dump complete
  
