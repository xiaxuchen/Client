package com.cxyz.homepage.dto;

import java.io.Serializable;

/**
 * GradeTaskDto中的考勤任务
 */
public class LessonDto implements Serializable{

    private Integer id;//任务id

    private String name;//课程名称

    private String num;//编号

    private String checkerName;//考勤人名字

    private String checkerId;//考勤人id

    private Integer checkerType;//考勤人类型

    private String roomName;//房间名

    private Integer roomId;//房间id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public Integer getCheckerType() {
        return checkerType;
    }

    public void setCheckerType(Integer checkerType) {
        this.checkerType = checkerType;
    }

    @Override
    public String toString() {
        return "LessonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", checkerName='" + checkerName + '\'' +
                ", checkerId='" + checkerId + '\'' +
                ", checkerType=" + checkerType +
                ", roomName='" + roomName + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
