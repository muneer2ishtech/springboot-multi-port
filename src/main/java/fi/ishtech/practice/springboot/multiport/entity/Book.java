package fi.ishtech.practice.springboot.multiport.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_book")
@DynamicInsert
@DynamicUpdate
@Data
public class Book implements Serializable {

	private static final long serialVersionUID = -4073976845697599055L;

	@Id
	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false, precision = 4, columnDefinition = "SMALLINT")
	private Short year;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

}
