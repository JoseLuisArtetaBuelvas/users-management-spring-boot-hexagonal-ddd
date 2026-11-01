-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios y Universidades - Arquitectura Hexagonal
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$placeholderHashReplaceWithRealBCryptHash',
    'ADMIN',
    'ACTIVE'
);

-- =============================================
-- Tabla de universidades
-- =============================================
CREATE TABLE IF NOT EXISTS universities (
    id              VARCHAR(36)  NOT NULL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    category        ENUM('PUBLICA', 'PRIVADA') NOT NULL,
    website         VARCHAR(150) NOT NULL,
    rector          VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL,
    access          ENUM('EXAMEN_ADMISION', 'ICFES', 'INGRESO_DIRECTO') NOT NULL,
    phone           VARCHAR(10)  NOT NULL,
    city            VARCHAR(100) NOT NULL,
    careers_count   INT NOT NULL DEFAULT 0,
    campuses_count  INT NOT NULL DEFAULT 0,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_universities_city (city),
    INDEX idx_universities_category (category),
    CONSTRAINT chk_universities_careers_count CHECK (careers_count >= 0),
    CONSTRAINT chk_universities_campuses_count CHECK (campuses_count >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Datos iniciales de ejemplo
INSERT IGNORE INTO universities
    (id, name, category, website, rector, email, access, phone, city, careers_count, campuses_count)
VALUES
    ('00000000-0000-0000-0000-000000000101', 'Universidad de Cartagena', 'PUBLICA', 'https://unicartagena.edu.co', 'William Malkun Castillejo', 'rectoria@unicartagena.edu.co', 'ICFES', '6056699800', 'Cartagena', 40, 4),
    ('00000000-0000-0000-0000-000000000102', 'Universidad del Valle', 'PUBLICA', 'https://univalle.edu.co', 'Guillermo Murillo Vargas', 'rectoria@univalle.edu.co', 'ICFES', '6023212100', 'Cali', 65, 8),
    ('00000000-0000-0000-0000-000000000103', 'Pontificia Universidad Javeriana', 'PRIVADA', 'https://javeriana.edu.co', 'Luis Fernando Munera', 'contacto@javeriana.edu.co', 'INGRESO_DIRECTO', '6013208320', 'Bogota', 45, 2);


