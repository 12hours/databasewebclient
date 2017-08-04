'use strict';

module.exports = function (grunt) {
    require('time-grunt')(grunt);
    require('jit-grunt')(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish')
            },
            all: {
                src: [
                    'Gruntfile.js',
                    'app/scripts/*.js'
                ]
            }
        },

        clean: {
            build: {
                src: ['dist/']
            }
        },

        copy: {
            dist: {
                cwd: 'app',
                src: ['*.html', 'content/*'],
                dest: 'dist',
                expand: true
            },
            libs: {
                cwd: 'app/js-libs',
                src: ['*.js'],
                dest: 'dist/js',
                expand: true
            },
            scripts: {
                cwd: 'app/scripts',
                src: ['*.js'],
                dest: 'dist/js',
                expand: true
            },
            styles: {
                cwd: 'app',
                src: ['css/*.css'],
                dest: 'dist',
                expand: true
            },
            fonts: {
                files: [
                    {
                        //for bootstrap fonts
                        expand: true,
                        dot: true,
                        cwd: 'app',
                        src: ['fonts/*.*'],
                        dest: 'dist'
                    }
                ]
            }
        },

        watch: {
            copy: {
                files: ['app/**', '!app/**/*.css', '!app/**/*.js'],
                tasks: ['build']
            },

            scripts: {
                files: ['app/scripts/app.js'],
                tasks: ['build']
            },

            styles: {
                files: ['app/css/*.css'],
                tasks: ['build']
            },

            livereload: {
                options: {
                    livereload: '<%= connect.options.livereload %>'
                },

                files: [
                    'app/{,*/}*.html',
                    '.tmp/styles/{,*/}*.css',
                    'app/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
                ]
            }
        },

        connect: {
            options: {
                port: 9000,
                // Change this to '0.0.0.0' to access the server from outside.
                hostname: 'localhost',
                livereload: 35729
            },

            dist: {
                options: {
                    open: true,
                    base: {
                        path: 'dist',
                        options: {
                            index: 'index.html',
                            maxAge: 300000
                        }
                    }
                }
            }
        },
    });

    grunt.registerTask('test', [
        'jshint'
    ]);

    grunt.registerTask('cln', [
        'clean'
    ])


    grunt.registerTask('build', [
        'clean',
        'copy'
    ]);

    grunt.registerTask('default', [
        'build'
    ]);

    grunt.registerTask('serve',['connect:dist','watch']);

};