# mvp+dagger2+rxandrod

### 环境搭建
- 创建config.grdle，统一三方库版本
- 在项目根目录的build.gradle文件引入  
  *** 
      apply from: "config.gradle" 
  ***
  
- 在应用目录的build.gradle文件配置
    1. 配置Android基本
    ***
        android {
            compileSdkVersion rootProject.ext.android.compileSdkVersion
            buildToolsVersion rootProject.ext.android.buildToolsVersion
            defaultConfig {
                applicationId rootProject.ext.android.applicationId
                minSdkVersion rootProject.ext.android.minSdkVersion
                targetSdkVersion rootProject.ext.android.targetSdkVersion
                versionCode rootProject.ext.android.versionCode
                versionName rootProject.ext.android.versionName
            }
            buildTypes {
                release {
                    minifyEnabled false
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                }
            }
        }
    ***
    2. 三方库的引用
    ***
       dependencies {
           compile fileTree(dir: 'libs', include: ['*.jar'])
           compile rootProject.ext.dependencies["appcompat-v7"]
           compile rootProject.ext.dependencies["constraint-layout"]
       
           //dagger
           compile rootProject.ext.dependencies["dagger"]
           apt rootProject.ext.dependencies["dagger-compiler"]
           provided rootProject.ext.dependencies["javax.annotation-api"]
       
           //ButterKnife
           compile rootProject.ext.dependencies["butterknife"]
           apt rootProject.ext.dependencies["butterknife-compiler"]
       
       }
    ***

    