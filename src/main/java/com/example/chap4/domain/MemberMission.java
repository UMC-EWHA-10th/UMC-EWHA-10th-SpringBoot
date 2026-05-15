package domain;

import com.example.chap4.domain.Mission;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.metamodel.SingularAttribute;
import org.apache.catalina.session.StoreBase;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class MemberMission {
    public SingularAttribute<AbstractPersistable, Serializable> getId() {
        return id;
    }

    // MemberMission.java 내부 예시

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission; // <-- 변수명이 mission인지 확인!

    public Mission getMission() { // <-- 반환 타입도 Mission이어야 함
        return mission;
    }
}
