package com.quetinkee.eshop.utils.helpers;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * Support class for inserting new bouquet review
 */
public class ReviewSubmit implements Serializable {

  @NotBlank(message = "Napište vaši recenzi")
  @Basic(optional = false)
  @Column(columnDefinition="TEXT", nullable = false)
  @Length(max = 2048, message = "Recenze je příliš dlouhá")
  private String message;

  @NotNull(message = "Zadejte hodnocení kytice")
  @Min(value = 0, message = "Příliš malé hodnocení")
  @Max(value = 5, message = "Příliš velké hodnocení")
  private Integer rating;

  public ReviewSubmit() {
  }

  public ReviewSubmit(String message, Integer rating) {
    this.message = message;
    this.rating = rating;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getRating() {
    return this.rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}
