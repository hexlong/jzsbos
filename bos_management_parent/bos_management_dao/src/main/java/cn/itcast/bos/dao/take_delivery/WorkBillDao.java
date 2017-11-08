package cn.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.WorkBill;

public interface WorkBillDao extends JpaRepository<WorkBill, Integer> {

}
