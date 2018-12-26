/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guiabolso.model;

/**
 *
 * @author daniel.savia
 */
public class AmazonDe extends ExtractISBN {

    @Override
    public String getValue(String url) {
        return DEFAULT_VALUE;
    }

}
