package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class RedditSearchTest extends BaseTest {

    @Test
    @Parameters({"query1"})
    public void searchMultipleQueries(@Optional("Grid") String query1) {
        driver.get("https://vaadin.com");

        WebElement docsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.haas-button.haas-nav-button[href='/docs/latest/']")));
        docsLink.click();

        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='OK']")));
            cookieButton.click();
            System.out.println("✅ Zamknięto popup cookies.");
        } catch (TimeoutException e) {
            System.out.println("ℹ️ Popup cookies się nie pojawił.");
        }

        // Kliknij ikonę wyszukiwania
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("docs-search-btn")));
        searchIcon.click();

        // Szukaj pierwszego zapytania
        performSearchAndPrintResults(query1);

        // Wyczyść pole ESC+ESC
        WebElement searchInput = driver.findElement(By.cssSelector("input[type='search'][placeholder='Search documentation']"));
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys(Keys.ESCAPE);

        // Szukaj drugiego zapytania
//        performSearchAndPrintResults(query2a);

        // Ostatni krok: wpisz specjalną frazę
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys(Keys.ESCAPE);
        searchInput.sendKeys("Now smile for the camera!");

        // Zrób screenshot
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("camera_smile.png"));
            System.out.println("📸 Screenshot zapisany jako camera_smile.png");
        } catch (IOException e) {
            System.out.println("❌ Nie udało się zapisać screenshota.");
            e.printStackTrace();
        }

        // Zamknij przeglądarkę (ręcznie — nie przez AfterMethod)
        driver.quit();
    }

    private void performSearchAndPrintResults(String query) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[type='search'][placeholder='Search documentation']")));
            input.sendKeys(query);
        } catch (TimeoutException e) {
            System.out.println("❌ Nie udało się znaleźć pola wyszukiwania.");
            return;
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[role='option']")));

        List<WebElement> results = driver.findElements(By.cssSelector("li[role='option']"));
        if (results.isEmpty()) {
            System.out.println("❌ Brak wyników dla: " + query);
        } else {
            System.out.println("\n🔍 Wyniki dla frazy: " + query);
            for (WebElement result : results) {
                try {
                    String title = result.findElement(By.cssSelector("div[class*='Title']")).getText();
                    String link = result.findElement(By.cssSelector("a")).getAttribute("href");
                    System.out.println("• " + title + " → " + link);
                } catch (Exception e) {
                    System.out.println("⚠️ Błąd przy parsowaniu wyniku.");
                }
            }
        }
    }
    String newOutput =  "{\n" +
                        "  \"ts\" : \"2026-03-04T13:58:22.687948+01:00\",\n" +
                        "  \"level\" : \"ERROR\",\n" +
                        "  \"logger\" : \"p.b.n.t.e.h.GlobalServiceErrorMailAspect\",\n" +
                        "  \"msg\" : \"AOP caught exception (eventId=1d9e9c63-edaa-4181-986b-38e1c59dfc41, projectId=null) in RunResultProcessor.process(..)\",\n" +
                        "  \"eventId\" : \"1d9e9c63-edaa-4181-986b-38e1c59dfc41\",\n" +
                        "  \"exception\" : \"java.lang.ClassNotFoundException: pl.b2b.net.testfactory.model.testCase.QCorrelationKey\\n\\t... 58 common frames omitted\\nWrapped by: java.lang.NoClassDefFoundError: pl/b2b/net/testfactory/model/testCase/QCorrelationKey\\n\\tat pl.b2b.net.testfactory.repository.CorrelationKeyCustomRepositoryImpl.getByCorrelationId(CorrelationKeyCustomRepositoryImpl.java:25)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(NativeMethodAccessorImpl.java)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\\n\\tat jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:569)\\n\\tat org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:360)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\\n\\tat org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:138)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\\n\\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)\\n\\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\\n\\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)\\n\\tat pl.b2b.net.testfactory.repository.CorrelationKeyCustomRepositoryImpl$$SpringCGLIB$$0.getByCorrelationId(<generated>)\\n\\tat pl.b2b.net.testfactory.service.messaging.RunResultProcessor.processMessage(RunResultProcessor.java:88)\\n\\tat pl.b2b.net.testfactory.service.messaging.RunResultProcessor.process(RunResultProcessor.java:58)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(NativeMethodAccessorImpl.java)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\\n\\tat jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:569)\\n\\tat org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:360)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:196)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\\n\\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)\\n\\tat pl.b2b.net.testfactory.exception.handler.GlobalServiceErrorMailAspect.aroundService(GlobalServiceErrorMailAspect.java:44)\\n\\tat jdk.internal.reflect.GeneratedMethodAccessor74.invoke(Unknown Source)\\n\\tat jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:569)\\n\\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:649)\\n\\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:631)\\n\\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:71)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\\n\\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:380)\\n\\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\\n\\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\\n\\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)\\n\\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:728)\\n\\tat pl.b2b.net.testfactory.service.messaging.RunResultProcessor$$SpringCGLIB$$0.process(<generated>)\\n\\tat pl.b2b.net.testfactory.service.messaging.listener.RunResultListener.onRunResult(RunResultListener.java:21)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(NativeMethodAccessorImpl.java)\\n\\tat jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\\n\\tat jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\\n\\tat java.lang.reflect.Method.invoke(Method.java:569)\\n\\tat org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:169)\\n\\tat org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:119)\\n\\tat org.springframework.jms.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:110)\\n\\tat org.springframework.jms.listener.adapter.MessagingMessageListenerAdapter.onMessage(MessagingMessageListenerAdapter.java:84)\\n\\tat org.springframework.jms.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:819)\\n\\tat org.springframework.jms.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:776)\\n\\tat org.springframework.jms.listener.AbstractMessageListenerContainer.doExecuteListener(AbstractMessageListenerContainer.java:754)\\n\\tat org.springframework.jms.listener.AbstractPollingMessageListenerContainer.doReceiveAndExecute(AbstractPollingMessageListenerContainer.java:333)\\n\\tat org.springframework.jms.listener.AbstractPollingMessageListenerContainer.receiveAndExecute(AbstractPollingMessageListenerContainer.java:270)\\n\\tat org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.invokeListener(DefaultMessageListenerContainer.java:1427)\\n\\tat org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.executeOngoingLoop(DefaultMessageListenerContainer.java:1417)\\n\\tat org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.run(DefaultMessageListenerContainer.java:1294)\\n\\tat java.lang.Thread.run(Thread.java:840)\\n\"\n" +
                        "}";
}
