package stu.napls.boostimnode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.boostimnode.model.Node;

import java.util.List;

public interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findByOrderByClientNumber();
}