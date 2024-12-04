//package com.skmservice.domain.file.entity;
//
//import com.skmservice.domain.post.entity.Post;
//import com.skmservice.global.common.BaseEntity;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "file")
//public class File extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post; // Post 엔티티와의 다대일 관계
//
//    @Column(name = "file_name")
//    private String fileName;
//
//    @Column(name = "file_path")
//    private String filePath;
//
//    @Column(name = "file_type")
//    private String fileType;
//}
