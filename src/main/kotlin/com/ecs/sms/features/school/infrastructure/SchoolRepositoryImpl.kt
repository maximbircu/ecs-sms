package com.ecs.sms.features.school.infrastructure

import com.ecs.sms.features.school.domain.*
import com.ecs.sms.features.school.domain.models.*
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryCourseInstanceRepository : CourseInstanceRepository {
    private val data = ConcurrentHashMap<String, CourseInstance>()

    override fun findAll() = data.values.toList()
    override fun findById(id: String) = data[id]
    override fun save(instance: CourseInstance): CourseInstance {
        data[instance.id] = instance
        return instance
    }
    override fun deleteById(id: String) { data.remove(id) }
}

// --- STUDENTS & ENROLLMENTS ---
@Repository
class InMemoryStudentRepository : StudentRepository {
    private val data = ConcurrentHashMap<String, Student>()

    override fun findAll() = data.values.toList()
    override fun findById(id: String) = data[id]
    override fun save(student: Student): Student {
        data[student.id] = student
        return student
    }
    override fun deleteById(id: String) { data.remove(id) }
}

@Repository
class InMemoryEnrollmentRepository : EnrollmentRepository {
    private val enrollments = mutableListOf<Enrollment>()

    override fun findByCourseInstanceId(courseId: String) =
        enrollments.filter { it.courseInstanceId == courseId }

    override fun findByStudentId(studentId: String) =
        enrollments.filter { it.studentId == studentId }

    override fun save(enrollment: Enrollment): Enrollment {
        enrollments.removeIf {
            it.courseInstanceId == enrollment.courseInstanceId && it.studentId == enrollment.studentId
        }
        enrollments.add(enrollment)
        return enrollment
    }

    override fun delete(courseId: String, studentId: String) {
        enrollments.removeIf { it.courseInstanceId == courseId && it.studentId == studentId }
    }
}

// --- TEACHERS ---
@Repository
class InMemoryTeacherRepository : TeacherRepository {
    private val data = ConcurrentHashMap<String, Teacher>()

    override fun findAll() = data.values.toList()
    override fun findById(id: String) = data[id]
    override fun save(teacher: Teacher): Teacher {
        data[teacher.id] = teacher
        return teacher
    }
    override fun deleteById(id: String) { data.remove(id) }
}

// --- LESSONS ---
@Repository
class InMemoryLessonRepository : LessonRepository {
    private val data = ConcurrentHashMap<String, Lesson>()

    override fun findAll() = data.values.toList()
    override fun findById(id: String) = data[id]
    override fun findByCourseInstanceId(courseId: String) =
        data.values.filter { it.courseInstanceId == courseId }

    override fun save(lesson: Lesson): Lesson {
        data[lesson.id] = lesson
        return lesson
    }
    override fun deleteById(id: String) { data.remove(id) }
}