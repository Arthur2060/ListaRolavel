package com.example.listarolavel.data.mapper

import com.example.listarolavel.data.local.entity.AlunoEntity
import com.example.listarolavel.data.remote.dto.AlunoDto
import com.example.listarolavel.domain.model.Aluno

fun AlunoDto.toEntity(
    pending: Boolean = false,
    oldLocalPath: String? = null
) = AlunoEntity(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)

fun AlunoDto.toDomain() = Aluno(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)

fun AlunoEntity.toDomain() = Aluno(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)

fun AlunoEntity.toDto() = AlunoDto(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)

fun Aluno.toEntity(
    pending: Boolean
) = AlunoEntity(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)

fun Aluno.toDto() = AlunoDto(
    id = id,
    nome = nome,
    curso = curso,
    notaMedia = notaMedia,
    faltasTotais = faltasTotais,
    senha = senha,
    pendingSync = pendingSync,
    deleted = deleted,
    updatedAt = updatedAt,
    login = login
)