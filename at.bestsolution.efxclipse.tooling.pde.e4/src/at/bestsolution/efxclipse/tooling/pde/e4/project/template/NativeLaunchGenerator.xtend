package at.bestsolution.efxclipse.tooling.pde.e4.project.template

import at.bestsolution.efxclipse.tooling.rrobot.model.task.Generator
import at.bestsolution.efxclipse.tooling.rrobot.model.task.DynamicFile
import java.util.Map
import java.io.ByteArrayInputStream

class NativeLaunchGenerator implements Generator<DynamicFile> {
	
	override generate(DynamicFile file, Map<String,Object> data) {
		val projectName = data.get("BundleProject_projectName") as String;
		val productName = data.get("BundleProject_productName") as String;
		val vendorName = data.get("BundleProject_bundleVendor") as String;
		val osArch = System::getProperty("osgi.os")+"."+System::getProperty("osgi.ws")+"."+System::getProperty("osgi.arch");
		val launcherdata = new NativeLaunchData("../"+projectName+".product/target/"+osArch+"/eclipse", productName, vendorName);
		val gen = new NativeLaunchGenerator();
		return new ByteArrayInputStream(gen.generate(launcherdata).toString.bytes);
	}
	
	def generate(NativeLaunchData data) '''<project name="native-build" default="do-deploy" basedir="."  xmlns:fx="javafx:com.sun.javafx.tools.ant">
	<property name="eclipse-app-dir" value="«data.tychoOutDir»" />
	
	<target name="init-fx-tasks">
		<path id="fxant">
			<filelist>
				<file name="${java.home}\..\lib\ant-javafx.jar"/>
				<file name="${java.home}\lib\jfxrt.jar"/>
			</filelist>
		</path>
	
		<taskdef resource="com/sun/javafx/tools/ant/antlib.xml"      
			uri="javafx:com.sun.javafx.tools.ant"
			classpathref="fxant"/>
		<taskdef name="configIni" classpath="at.bestsolution.efxclipse.tooling.build-0.0.1.jar" classname="at.bestsolution.efxclipse.tooling.build.ConfigFixTask" />
	</target>
	
	<target name="config-fix" depends="init-fx-tasks">
		<configIni rootfolder="${eclipse-app-dir}"/>
	</target>
	
	<target name="do-deploy" depends="config-fix, init-fx-tasks">
		<fx:resources id="appRes">
			<fx:fileset dir="." includes="fx-osgi-launch.jar"/>
			<fx:fileset dir="${eclipse-app-dir}" includes="**/*"/>
		</fx:resources>
		
		<fx:application id="fxApplication"
								name="«data.productName»"
								mainClass="org.eclipse.equinox.launcher.Main"
								toolkit="swing"
						
							/>
		
		<fx:deploy
			embedJNLP="false"
			extension="false"
			includeDT="false"
			offlineAllowed="true"
			outdir="${basedir}/deploy"
			outfile="fix-ide" 
			nativeBundles="all"
			updatemode="background"
			>
			
			<fx:info title="«data.productName»" vendor="«data.vendorName»"/>
			<fx:application refid="fxApplication"/>
			<fx:resources refid="appRes"/>
		</fx:deploy>
	</target>
</project>'''
}