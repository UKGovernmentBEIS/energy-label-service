const sass = require('gulp-sass');
const postcss = require('gulp-postcss');
const gulp = require('gulp');
const sourcemaps = require('gulp-sourcemaps');
const autoprefixer = require('autoprefixer');
const rename = require("gulp-rename");

const sassOptions = {
  outputStyle: 'expanded',
  includePath: 'src/main/resources/scss'
};

const sassGlobPattern = 'src/main/resources/scss/*.scss';
const freemarkerGlobPattern = 'src/main/resources/templates/**/*.ftl';

function compileSass(exitOnError) {
  const plugins = [
    autoprefixer({
      browsers: ['last 3 versions', '> 0.1%', 'Firefox ESR'],
      grid: true
    })
  ];

  let sassTask = sass(sassOptions);
  if (!exitOnError) {
    //Without an error handler specified, the task will exit on error, which we want for the "buildAll" task
    sassTask = sassTask.on('error', sass.logError);
  }

  return gulp.src(sassGlobPattern, {base: "."})
    .pipe(sourcemaps.init())
    .pipe(sassTask)
    .pipe(postcss(plugins))
    .pipe(sourcemaps.write())
    .pipe(rename(path => {
      //E.g. core\src\main\resources\sass\core -> core\src\main\resources\public\assets\static\css
      path.dirname = path.dirname.replace(/([\/\\])scss[\/\\]?/, '$1public$1assets$1static$1css');
    }))
    .pipe(gulp.dest('./'))
    //For IntelliJ run
    .pipe(rename(path => {
      //E.g. core\src\main\resources\sass\core -> core\out\production\resources\static\core
      path.dirname = path.dirname.replace(/([\/\\])?src([\/\\])main[\/\\]/, '$2out$2production$2');
    }))
    .pipe(gulp.dest('./'));
}

gulp.task('sass', () => {
  return compileSass(false);
});

gulp.task('copyTemplates', () => {
  return gulp.src(freemarkerGlobPattern, {base: "."})
  .pipe(rename(path => {
    //E.g. core\src\main\resources\templates\core\form.ftl -> core\out\production\resources\templates\core\form.ftl
    path.dirname = path.dirname.replace(/([\/\\])src[\/\\]main[\/\\]/, '$1out$1production$1');
  }))
  .pipe(gulp.dest('./'));
});

gulp.task('watch', callback => {
  gulp.watch(sassGlobPattern, ['sass']);
  //copyTemplates is only in 'watch', not 'buildAll', because it's only needed during development for refreshing templates
  gulp.watch(freemarkerGlobPattern, ['copyTemplates']);
});

gulp.task('buildAll', () => {
  return compileSass(true);
//Additional Gulp tasks should be called here
});