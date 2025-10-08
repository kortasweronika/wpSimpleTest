package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.internal.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestNgClasspathCheck {

    @Test
    public void onlyOneTestNgOnClasspath() throws Exception {
        int count = 0; List<String> all = new ArrayList<>();
        var cl = Thread.currentThread().getContextClassLoader();
        var en = cl.getResources("org/testng/TestNG.class");
        while (en.hasMoreElements()) { all.add(en.nextElement().toString()); count++; }

        String locCore = org.testng.TestNG.class.getProtectionDomain().getCodeSource().getLocation().toString();
        String locAnno = org.testng.annotations.Test.class.getProtectionDomain().getCodeSource().getLocation().toString();

        System.out.println("TestNG.VERSION=" + Version.VERSION);
        System.out.println("TestNG.class from=" + locCore);
        System.out.println("@Test.class  from=" + locAnno);
        System.out.println("TestNG.class count=" + count + " -> " + all);

        Assert.assertEquals(count, 1, "Expected exactly one TestNG on classpath, got " + count + " -> " + all);
        Assert.assertEquals(locCore, locAnno, "Core and annotations must come from the same location");
    }

    import org.testng.Assert;
import org.testng.internal.Version;
// ...

    @Test
    public void extractAndSearchComponent() throws IOException, InterruptedException {
        // --- DIAGNOSTYKA TestNG: ma być dokładnie 1 kopia na classpath ---
        int count = 0;
        java.util.List<String> all = new java.util.ArrayList<>();
        var cl = Thread.currentThread().getContextClassLoader();
        var en = cl.getResources("org/testng/TestNG.class");
        while (en.hasMoreElements()) { all.add(en.nextElement().toString()); count++; }

        String locCore  = org.testng.TestNG.class.getProtectionDomain().getCodeSource().getLocation().toString();
        String locAnno  = org.testng.annotations.Test.class.getProtectionDomain().getCodeSource().getLocation().toString();
        System.out.println("TestNG.VERSION=" + Version.VERSION);
        System.out.println("TestNG.class from=" + locCore);
        System.out.println("@Test.class  from=" + locAnno);
        System.out.println("TestNG.class count=" + count + " -> " + all);

        // 🔴 PRZED patchem to zwykle będzie >1 (fail). Po patchu ==1 (pass).
        Assert.assertEquals(count, 1, "Expected exactly one TestNG on classpath, got " + count + " -> " + all);
        Assert.assertEquals(locCore, locAnno, "Core and annotations must come from the same location");

    }

    @Test
    public void writesOutputPropsToRunnerWorkdir() throws Exception {
        String wd = pl.b2b.testfactory.testrunner.TestNgRunner.getCurrentWorkDir();
        org.testng.Assert.assertNotNull(wd, "Runner workdir should be set by listener");
        java.util.Properties p = new java.util.Properties();
        p.setProperty("hello", "world");
        try (var out = new java.io.FileOutputStream(new java.io.File(wd, ".tf-output.properties"))) {
            p.store(out, "");
        }
    }


}
