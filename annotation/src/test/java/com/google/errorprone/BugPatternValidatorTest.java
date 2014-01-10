package com.google.errorprone;

import com.google.errorprone.BugPattern.Category;
import com.google.errorprone.BugPattern.LinkType;
import com.google.errorprone.BugPattern.MaturityLevel;
import com.google.errorprone.BugPattern.SeverityLevel;
import com.google.errorprone.BugPattern.Suppressibility;

import org.junit.Test;

public class BugPatternValidatorTest {

  private @interface CustomSuppressionAnnotation {}

  @Test
  public void basicBugPattern() throws Exception {
    @BugPattern(
        name = "BasicBugPattern", summary = "Simplest possible BugPattern",
        explanation = "Simplest possible BugPattern ",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test
  public void linkTypeNoneAndNoLink() throws Exception {
    @BugPattern(
        name = "LinkTypeNoneAndNoLink", summary = "linkType none and no link",
        explanation = "linkType none and no link",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        linkType = LinkType.NONE)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void linkTypeNoneButIncludesLink() throws Exception {
    @BugPattern(
        name = "LinkTypeNoneButIncludesLink", summary = "linkType none but includes link",
        explanation = "linkType none but includes link",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        linkType = LinkType.NONE, link = "http://foo")
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test
  public void linkTypeCustomAndIncludesLink() throws Exception {
    @BugPattern(
        name = "LinkTypeCustomAndIncludesLink", summary = "linkType custom and includes link",
        explanation = "linkType custom and includes link",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        linkType = LinkType.CUSTOM, link = "http://foo")
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void linkTypeCustomButNoLink() throws Exception {
    @BugPattern(
        name = "LinkTypeCustomButNoLink", summary = "linkType custom but no link",
        explanation = "linkType custom but no link",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        linkType = LinkType.CUSTOM)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void suppressWarningsButIncludesCustomAnnotation() throws Exception {
    @BugPattern(
        name = "SuppressWarningsButIncludesCustomAnnotation",
        summary = "Uses SuppressWarnings but includes custom suppression annotation",
        explanation = "Uses SuppressWarnings but includes custom suppression annotation",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.SUPPRESS_WARNINGS,
        customSuppressionAnnotation = CustomSuppressionAnnotation.class)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test()
  public void unsuppressible() throws Exception {
    @BugPattern(
        name = "Unsuppressible",
        summary = "An unsuppressible BugPattern",
        explanation = "An unsuppressible BugPattern",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.UNSUPPRESSIBLE)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void unsuppressibleButIncludesCustomAnnotation() throws Exception {
    @BugPattern(
        name = "unsuppressibleButIncludesCustomAnnotation",
        summary = "Unsuppressible but includes custom suppression annotation",
        explanation = "Unsuppressible but includes custom suppression annotation",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.UNSUPPRESSIBLE,
        customSuppressionAnnotation = CustomSuppressionAnnotation.class)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test
  public void customSuppressionAnnotation() throws Exception {
    @BugPattern(
        name = "customSuppressionAnnotation",
        summary = "Uses a custom suppression annotation",
        explanation = "Uses a custom suppression annotation",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.CUSTOM_ANNOTATION,
        customSuppressionAnnotation = CustomSuppressionAnnotation.class)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void customSuppressionAnnotationButSuppressWarnings() throws Exception {
    @BugPattern(
        name = "customSuppressionAnnotationButSuppressWarnings",
        summary = "Specifies a custom suppression annotation of @SuppressWarnings",
        explanation = "Specifies a custom suppression annotation of @SuppressWarnings",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.CUSTOM_ANNOTATION,
        customSuppressionAnnotation = SuppressWarnings.class)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }

  @Test(expected = ValidationException.class)
  public void customSuppressionAnnotationButNoneSpecified() throws Exception {
    @BugPattern(
        name = "customSuppressionAnnotationButNoneSpecified",
        summary = "Sets suppressibility to custom but doesn't provide a custom annotation",
        explanation = "Sets suppressibility to custom but doesn't provide a custom annotation",
        category = Category.ONE_OFF, maturity = MaturityLevel.EXPERIMENTAL,
        severity = SeverityLevel.ERROR,
        suppressibility = Suppressibility.CUSTOM_ANNOTATION)
    final class BugPatternTestClass {}

    BugPattern annotation = BugPatternTestClass.class.getAnnotation(BugPattern.class);
    BugPatternValidator.validate(annotation);
  }



}
