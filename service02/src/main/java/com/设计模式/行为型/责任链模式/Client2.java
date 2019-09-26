package com.设计模式.行为型.责任链模式;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/17 21:02
 * @Description:
 * 例子：小明是一个初级软件开发工程师，假设现在公司有几个需求需要开发，这些需求有些比较容易，有些比较困难。
 * 开发团队中有初级工程师，中级工程师，高级工程师和资深工程师，按需求难度进行划分，
 * 初级工程师能解决就交由初级工程师，
 * 否则将需求转交到更高一级工程师，
 * 直到资深工程师。
 * 如果资深工程师都无法解决这个需求，则要求产品更改需求。
 *
 * 分析：以上例子是一个典型的 责任链模式 需求，工程师对应节点处理者，需求对应请求。
 * 利用 责任链模式，只需将需求一个一个交由初级工程师（小明），
 * 需求就会自动地传递给具备相应能力进行开发的工程师手中，
 * 或者需求无法解决，则交由产品更改。
 */
public class Client2 {
    public static void main(String[] args) {
        Engineer xiaoming = new Engineer.Builder()
                .addEngineer(new JuniorEngineer())
                .addEngineer(new MidEngineer())
                .addEngineer(new SeniorEngineer())
                .addEngineer(new ProfessionalEngineer())
                .build();
        IProject easyProject = new EasyProject();
        IProject normalProject = new NormalProject();
        IProject hardProject = new HardProject();
        IProject tooHardProject = new TooHardProject();
        IProject beyondProject = new BeyondProject();

        if(!xiaoming.doWork(easyProject)){
            System.out.println("tell Product Manager: easy project can not complete");
        }
        if(!xiaoming.doWork(normalProject)){
            System.out.println("tell Product Manager: normal project can not complete");
        }
        if(!xiaoming.doWork(hardProject)){
            System.out.println("tell Product Manager: hard project can not complete");
        }
        if(!xiaoming.doWork(tooHardProject)){
            System.out.println("tell Product Manager: too hard project can not complete");
        }
        if(!xiaoming.doWork(beyondProject)){
            System.out.println("tell Product Manager: beyond project can not complete: "+beyondProject.difficulty());
        }
    }

    interface IProject {
        static int EASY = 0;
        static int NORMAL = 1;
        static int HARD = 2;
        static int TOO_HARD = 3;
        static int BEYOND = 4;

        // 项目难度
        int difficulty();
    }

    static class EasyProject implements IProject{

        @Override
        public int difficulty() {
            return IProject.EASY;
        }
    }
    static class NormalProject implements IProject{

        @Override
        public int difficulty() {
            return IProject.NORMAL;
        }
    }
    static class HardProject implements IProject{

        @Override
        public int difficulty() {
            return IProject.HARD;
        }
    }
    static class TooHardProject implements IProject{

        @Override
        public int difficulty() {
            return IProject.TOO_HARD;
        }
    }

    static class BeyondProject implements IProject{

        @Override
        public int difficulty() {
            return IProject.BEYOND;
        }
    }

    static abstract class Engineer {
        //下一个工程师
        private Engineer mSuccessor;

        public final boolean doWork(IProject project) {
            boolean bRet = false;
            do {
                if (this.filterProject(project)) {
                    //这个工程师能做就做
                    bRet = this.writeCode(project);
                    break;
                }
                if (this.mSuccessor != null) {
                    //这个工程师不能做就让下一个做
                    bRet = this.mSuccessor.doWork(project);
                    break;
                }
            } while (false);
            return bRet;
        }

        //当前这个工程师能不能干这个项目
        protected abstract boolean filterProject(IProject project);

        //当前这个工程师干项目
        protected abstract boolean writeCode(IProject project);

        public static class Builder {
            private Engineer mFirst;
            private Engineer mLast;

            public Builder addEngineer(Engineer engineer) {
                if (this.mFirst == null) {
                    this.mFirst = this.mLast = engineer;
                } else {
                    this.mLast.mSuccessor = engineer;
                    this.mLast = engineer;
                }
                return this;
            }

            public Engineer build() {
                return this.mFirst;
            }
        }
    }

    // 初级工程师
    static class JuniorEngineer extends Engineer {

        @Override
        protected boolean filterProject(IProject project) {
            return project.difficulty() <= IProject.EASY;
        }

        @Override
        protected boolean writeCode(IProject project) {
            System.out.println("Junior engineer completes project: " + project.difficulty());
            return true;
        }
    }

    // 中级工程师
    static class MidEngineer extends Engineer {

        @Override
        protected boolean filterProject(IProject project) {
            return project.difficulty() <= IProject.NORMAL;
        }

        @Override
        protected boolean writeCode(IProject project) {
            System.out.println("Middle level engineer completes project: " + project.difficulty());
            return true;
        }
    }

    // 高级工程师
    static class SeniorEngineer extends Engineer {

        @Override
        protected boolean filterProject(IProject project) {
            return project.difficulty() <= IProject.HARD;
        }

        @Override
        protected boolean writeCode(IProject project) {
            System.out.println("Senior engineer completes project: " + project.difficulty());
            return true;
        }
    }

    // 资深工程师
    static class ProfessionalEngineer extends Engineer {

        @Override
        protected boolean filterProject(IProject project) {
            return project.difficulty() <= IProject.TOO_HARD;
        }

        @Override
        protected boolean writeCode(IProject project) {
            System.out.println("Professional engineer completes project: " + project.difficulty());
            return true;
        }
    }
}
