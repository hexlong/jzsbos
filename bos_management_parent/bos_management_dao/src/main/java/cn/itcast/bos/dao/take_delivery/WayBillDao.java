package cn.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.WayBill;

public interface WayBillDao extends JpaRepository<WayBill, Integer> {

}
