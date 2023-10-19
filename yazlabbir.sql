--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-10-19 19:42:31

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

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 858 (class 1247 OID 16421)
-- Name: durumType; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."durumType" AS ENUM (
    'beklemede',
    'iptal',
    'kabul',
    'ret'
);


--
-- TOC entry 873 (class 1247 OID 16467)
-- Name: gonderen; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public.gonderen AS ENUM (
    'ogretmen',
    'ogrenci'
);


--
-- TOC entry 861 (class 1247 OID 16430)
-- Name: talepDurumlari; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE public."talepDurumlari" AS ENUM (
    'anlasma',
    'rastgele',
    'durdur'
);


--
-- TOC entry 222 (class 1259 OID 16463)
-- Name: anlasmalar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.anlasmalar (
    anlasma_no bigint NOT NULL,
    ogrenci_no bigint NOT NULL,
    ogretmen_no bigint NOT NULL,
    ders text NOT NULL,
    gonderen public.gonderen NOT NULL,
    durum public."talepDurumlari" NOT NULL,
    ogrenci_mesaj text NOT NULL,
    ogretmen_mesaj text NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16471)
-- Name: anlasmalar_anlasma_no_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.anlasmalar_anlasma_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4847 (class 0 OID 0)
-- Dependencies: 223
-- Name: anlasmalar_anlasma_no_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.anlasmalar_anlasma_no_seq OWNED BY public.anlasmalar.anlasma_no;


--
-- TOC entry 221 (class 1259 OID 16455)
-- Name: hocalar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.hocalar (
    sicil_no bigint NOT NULL,
    sifre text NOT NULL,
    ad text NOT NULL,
    soyad text NOT NULL,
    ilgi_alanlari text[],
    kontenjan_sayisi integer NOT NULL,
    acilan_dersler text[]
);


--
-- TOC entry 220 (class 1259 OID 16454)
-- Name: hocalar_sicil_no_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.hocalar_sicil_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4848 (class 0 OID 0)
-- Dependencies: 220
-- Name: hocalar_sicil_no_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.hocalar_sicil_no_seq OWNED BY public.hocalar.sicil_no;


--
-- TOC entry 225 (class 1259 OID 16491)
-- Name: kriter_formulleri_puanlari; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.kriter_formulleri_puanlari (
    kriter_no bigint NOT NULL,
    hoca_no bigint NOT NULL,
    ders text NOT NULL,
    katsayi integer NOT NULL
);


--
-- TOC entry 224 (class 1259 OID 16490)
-- Name: kriter_formulleri_puanlari_kriter_no_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.kriter_formulleri_puanlari_kriter_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4849 (class 0 OID 0)
-- Dependencies: 224
-- Name: kriter_formulleri_puanlari_kriter_no_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.kriter_formulleri_puanlari_kriter_no_seq OWNED BY public.kriter_formulleri_puanlari.kriter_no;


--
-- TOC entry 217 (class 1259 OID 16408)
-- Name: notlar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notlar (
    ogrenci_no bigint NOT NULL,
    ders_no text NOT NULL,
    "not" numeric(4,0) NOT NULL,
    harf_notu character(2)[] NOT NULL
);


--
-- TOC entry 216 (class 1259 OID 16400)
-- Name: ogrenciler; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ogrenciler (
    ogrenci_no bigint NOT NULL,
    sifre text NOT NULL,
    ad text NOT NULL,
    soyad text NOT NULL,
    ilgi_alanlari text[],
    pdf bytea,
    genel_not numeric(4,0) NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: ogrenciler_ogrenci_no_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ogrenciler_ogrenci_no_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4850 (class 0 OID 0)
-- Dependencies: 215
-- Name: ogrenciler_ogrenci_no_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ogrenciler_ogrenci_no_seq OWNED BY public.ogrenciler.ogrenci_no;


--
-- TOC entry 227 (class 1259 OID 16505)
-- Name: ornek_ogrenci; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ornek_ogrenci (
    id bigint NOT NULL,
    ad text NOT NULL,
    soyad text NOT NULL,
    sifre text NOT NULL
);


--
-- TOC entry 226 (class 1259 OID 16504)
-- Name: ornek_ogrenci_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ornek_ogrenci_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4851 (class 0 OID 0)
-- Dependencies: 226
-- Name: ornek_ogrenci_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ornek_ogrenci_id_seq OWNED BY public.ornek_ogrenci.id;


--
-- TOC entry 219 (class 1259 OID 16443)
-- Name: parametreler; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.parametreler (
    id bigint NOT NULL,
    durum public."talepDurumlari" NOT NULL,
    ayni_hoca_mult boolean DEFAULT false NOT NULL,
    ayni_ders_mult integer DEFAULT 1 NOT NULL,
    talep_maks_karakter integer DEFAULT 100 NOT NULL,
    ilgi_alanlari text[] NOT NULL,
    admin_sifresi text NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 16442)
-- Name: parametreler_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.parametreler_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4852 (class 0 OID 0)
-- Dependencies: 218
-- Name: parametreler_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.parametreler_id_seq OWNED BY public.parametreler.id;


--
-- TOC entry 4678 (class 2604 OID 16472)
-- Name: anlasmalar anlasma_no; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anlasmalar ALTER COLUMN anlasma_no SET DEFAULT nextval('public.anlasmalar_anlasma_no_seq'::regclass);


--
-- TOC entry 4677 (class 2604 OID 16458)
-- Name: hocalar sicil_no; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hocalar ALTER COLUMN sicil_no SET DEFAULT nextval('public.hocalar_sicil_no_seq'::regclass);


--
-- TOC entry 4679 (class 2604 OID 16494)
-- Name: kriter_formulleri_puanlari kriter_no; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.kriter_formulleri_puanlari ALTER COLUMN kriter_no SET DEFAULT nextval('public.kriter_formulleri_puanlari_kriter_no_seq'::regclass);


--
-- TOC entry 4672 (class 2604 OID 16403)
-- Name: ogrenciler ogrenci_no; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ogrenciler ALTER COLUMN ogrenci_no SET DEFAULT nextval('public.ogrenciler_ogrenci_no_seq'::regclass);


--
-- TOC entry 4680 (class 2604 OID 16508)
-- Name: ornek_ogrenci id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ornek_ogrenci ALTER COLUMN id SET DEFAULT nextval('public.ornek_ogrenci_id_seq'::regclass);


--
-- TOC entry 4673 (class 2604 OID 16446)
-- Name: parametreler id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.parametreler ALTER COLUMN id SET DEFAULT nextval('public.parametreler_id_seq'::regclass);


--
-- TOC entry 4690 (class 2606 OID 16479)
-- Name: anlasmalar anlasmalar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anlasmalar
    ADD CONSTRAINT anlasmalar_pkey PRIMARY KEY (anlasma_no);


--
-- TOC entry 4688 (class 2606 OID 16462)
-- Name: hocalar hocalar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.hocalar
    ADD CONSTRAINT hocalar_pkey PRIMARY KEY (sicil_no);


--
-- TOC entry 4692 (class 2606 OID 16498)
-- Name: kriter_formulleri_puanlari kriter_formulleri_puanlari_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.kriter_formulleri_puanlari
    ADD CONSTRAINT kriter_formulleri_puanlari_pkey PRIMARY KEY (kriter_no);


--
-- TOC entry 4684 (class 2606 OID 16414)
-- Name: notlar notlar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notlar
    ADD CONSTRAINT notlar_pkey PRIMARY KEY (ogrenci_no, ders_no);


--
-- TOC entry 4682 (class 2606 OID 16407)
-- Name: ogrenciler ogrenciler_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ogrenciler
    ADD CONSTRAINT ogrenciler_pkey PRIMARY KEY (ogrenci_no);


--
-- TOC entry 4694 (class 2606 OID 16512)
-- Name: ornek_ogrenci ornek_ogrenci_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ornek_ogrenci
    ADD CONSTRAINT ornek_ogrenci_pkey PRIMARY KEY (id);


--
-- TOC entry 4686 (class 2606 OID 16453)
-- Name: parametreler parametreler_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.parametreler
    ADD CONSTRAINT parametreler_pkey PRIMARY KEY (id);


--
-- TOC entry 4696 (class 2606 OID 16480)
-- Name: anlasmalar anlasmalar_ogrenci_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anlasmalar
    ADD CONSTRAINT anlasmalar_ogrenci_no_fkey FOREIGN KEY (ogrenci_no) REFERENCES public.ogrenciler(ogrenci_no) NOT VALID;


--
-- TOC entry 4697 (class 2606 OID 16485)
-- Name: anlasmalar anlasmalar_ogretmen_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.anlasmalar
    ADD CONSTRAINT anlasmalar_ogretmen_no_fkey FOREIGN KEY (ogretmen_no) REFERENCES public.hocalar(sicil_no) NOT VALID;


--
-- TOC entry 4698 (class 2606 OID 16499)
-- Name: kriter_formulleri_puanlari kriter_formulleri_puanlari_hoca_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.kriter_formulleri_puanlari
    ADD CONSTRAINT kriter_formulleri_puanlari_hoca_no_fkey FOREIGN KEY (hoca_no) REFERENCES public.hocalar(sicil_no);


--
-- TOC entry 4695 (class 2606 OID 16415)
-- Name: notlar notlar_ogrenci_no_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notlar
    ADD CONSTRAINT notlar_ogrenci_no_fkey FOREIGN KEY (ogrenci_no) REFERENCES public.ogrenciler(ogrenci_no) NOT VALID;


-- Completed on 2023-10-19 19:42:32

--
-- PostgreSQL database dump complete
--

