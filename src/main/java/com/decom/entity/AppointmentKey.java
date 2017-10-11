package com.decom.entity;

public class AppointmentKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.book_id
     *
     * @mbggenerated
     */
    protected Long bookId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment.student_id
     *
     * @mbggenerated
     */
    protected Long studentId;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table appointment
     *
     * @mbggenerated
     */
    public AppointmentKey(Long bookId, Long studentId) {
        this.bookId = bookId;
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.book_id
     *
     * @return the value of appointment.book_id
     *
     * @mbggenerated
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment.student_id
     *
     * @return the value of appointment.student_id
     *
     * @mbggenerated
     */
    public Long getStudentId() {
        return studentId;
    }
}