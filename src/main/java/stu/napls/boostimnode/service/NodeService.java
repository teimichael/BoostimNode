package stu.napls.boostimnode.service;

import stu.napls.boostimnode.model.Node;

public interface NodeService {
    Node findById(long id);

    Node findBestNode();

    Node update(Node node);
}
