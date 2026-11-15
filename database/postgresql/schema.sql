-- =============================================
-- Script de base de datos para PostgreSQL (Supabase)
-- Gestion de Usuarios y Universidades - Arquitectura Hexagonal
--
-- Ejecutar en Supabase: SQL Editor -> New query -> Run.
-- Supabase ya trae la base de datos "postgres", no se crea otra.
-- Equivale a src/main/resources/schema.sql (MySQL), adaptado a PostgreSQL:
-- los ENUM de MySQL se reemplazan por VARCHAR + CHECK.
-- =============================================

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL,
    status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'MEMBER', 'REVIEWER')),
    CONSTRAINT chk_users_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED'))
);

-- Supabase publica las tablas de "public" en su API REST. Con RLS activo y sin
-- politicas, esa API no puede leerlas; la aplicacion conecta como propietaria
-- (usuario postgres) y no se ve afectada.
ALTER TABLE users ENABLE ROW LEVEL SECURITY;

-- Usuario administrador inicial (password: Admin1234!) - hash BCrypt cost 12
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$hO1y1eGHaNCpqHg0ydkmjudjuSSd2PSygrf9.xGYaSXgA/c2Ugh9q',
    'ADMIN',
    'ACTIVE'
)
ON CONFLICT (id) DO NOTHING;

-- =============================================
-- Tabla de universidades
-- =============================================
CREATE TABLE IF NOT EXISTS universities (
    id              VARCHAR(36)  NOT NULL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    category        VARCHAR(20)  NOT NULL,
    website         VARCHAR(150) NOT NULL,
    rector          VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL,
    access          VARCHAR(30)  NOT NULL,
    phone           VARCHAR(10)  NOT NULL,
    city            VARCHAR(100) NOT NULL,
    careers_count   INT          NOT NULL DEFAULT 0,
    campuses_count  INT          NOT NULL DEFAULT 0,
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_universities_category CHECK (category IN ('PUBLICA', 'PRIVADA')),
    CONSTRAINT chk_universities_access CHECK (access IN ('EXAMEN_ADMISION', 'ICFES', 'INGRESO_DIRECTO')),
    CONSTRAINT chk_universities_careers_count CHECK (careers_count >= 0),
    CONSTRAINT chk_universities_campuses_count CHECK (campuses_count >= 0)
);

CREATE INDEX IF NOT EXISTS idx_universities_city ON universities (city);
CREATE INDEX IF NOT EXISTS idx_universities_category ON universities (category);

ALTER TABLE universities ENABLE ROW LEVEL SECURITY;

-- Datos iniciales de ejemplo
INSERT INTO universities
    (id, name, category, website, rector, email, access, phone, city, careers_count, campuses_count)
VALUES
    ('00000000-0000-0000-0000-000000000101', 'Universidad de Cartagena', 'PUBLICA', 'https://unicartagena.edu.co', 'William Malkun Castillejo', 'rectoria@unicartagena.edu.co', 'ICFES', '6056699800', 'Cartagena', 40, 4),
    ('00000000-0000-0000-0000-000000000102', 'Universidad del Valle', 'PUBLICA', 'https://univalle.edu.co', 'Guillermo Murillo Vargas', 'rectoria@univalle.edu.co', 'ICFES', '6023212100', 'Cali', 65, 8),
    ('00000000-0000-0000-0000-000000000103', 'Pontificia Universidad Javeriana', 'PRIVADA', 'https://javeriana.edu.co', 'Luis Fernando Munera', 'contacto@javeriana.edu.co', 'INGRESO_DIRECTO', '6013208320', 'Bogota', 45, 2)
ON CONFLICT (id) DO NOTHING;

SELECT id, name, category, city FROM universities;
