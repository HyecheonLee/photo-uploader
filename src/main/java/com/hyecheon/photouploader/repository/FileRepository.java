package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
