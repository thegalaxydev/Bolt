package mine.block.bolt.mixin;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Mixin Crash Report Helper
@Mixin(value = CrashReport.class)
public abstract class CrashReportMixin {
    @Shadow
    private StackTraceElement[] stackTrace;

    @Shadow
    @Final
    private List<CrashReportSection> otherSections;

    @Inject(method = "writeToFile", at = @At("HEAD"))
    private void bolt$addBranding(File file, CallbackInfoReturnable<Boolean> cir) {
        var branding = Bolt.CONFIG.modpackBranding;
        if (branding.enabled) {
            var section = new CrashReportSection("Modpack Information");
            section.add("Modpack Name", branding.modpackName);
            section.add("Modpack Version", branding.modpackVersion.semName);
            section.add("Modpack Authors", String.join(", ", branding.modpackAuthors));
            section.add("Modpack Website", branding.URLS.website);
            section.add("Modpack Support", branding.URLS.support);
            section.add("Modpack Repository", branding.URLS.repository);

            this.otherSections.add(section);
        }
    }

    @Inject(method = "addStackTrace(Ljava/lang/StringBuilder;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/util/crash/CrashReport;otherSections:Ljava/util/List;"))
    private void bolt$addTrace(StringBuilder crashReportBuilder, CallbackInfo ci) {
        int trailingNewlineCount = 0;
        if (crashReportBuilder.charAt(crashReportBuilder.length() - 1) == '\n') {
            crashReportBuilder.deleteCharAt(crashReportBuilder.length() - 1);
            trailingNewlineCount++;
        }
        if (crashReportBuilder.charAt(crashReportBuilder.length() - 1) == '\n') {
            crashReportBuilder.deleteCharAt(crashReportBuilder.length() - 1);
            trailingNewlineCount++;
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            crashReportBuilder.append("\nThe following mixins were found in the stacktrace:");

            try {
                List<String> classNames = new ArrayList<>();
                for (StackTraceElement el : this.stackTrace) {
                    if (!classNames.contains(el.getClassName())) {
                        classNames.add(el.getClassName());
                    }
                }

                boolean found = false;
                for (String className : classNames) {
                    ClassInfo classInfo = ClassInfo.fromCache(className);
                    if (classInfo != null) {
                        Object mixinInfoSetObject;
                        try {
                            Method getMixins = ClassInfo.class.getDeclaredMethod("getMixins");
                            getMixins.setAccessible(true);
                            mixinInfoSetObject = getMixins.invoke(classInfo);
                        } catch (Exception e) {
                            var mixinsField = ClassInfo.class.getDeclaredField("mixins");
                            mixinsField.setAccessible(true);
                            mixinInfoSetObject = mixinsField.get(classInfo);
                        }

                        @SuppressWarnings("unchecked") Set<IMixinInfo> mixinInfoSet = (Set<IMixinInfo>) mixinInfoSetObject;

                        if (mixinInfoSet.size() > 0) {
                            crashReportBuilder.append("\n\t");
                            crashReportBuilder.append(className);
                            crashReportBuilder.append(":");
                            for (IMixinInfo info : mixinInfoSet) {
                                crashReportBuilder.append("\n\t\t");
                                crashReportBuilder.append(info.getClassName());
                                crashReportBuilder.append(" (");
                                crashReportBuilder.append(info.getConfig().getName());
                                crashReportBuilder.append(")");
                            }
                            found = true;
                        }
                    }
                }

                if (!found) {
                    crashReportBuilder.append(" No Mixins Found");
                }
            } catch (Exception e) {
                crashReportBuilder.append(" Failed to find mixin metadata: ").append(e);
            }
        }
        crashReportBuilder.append("\n".repeat(trailingNewlineCount));
    }
}