package dao;

import model.Mercadoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MercadoriaDAO extends DAO {
    public MercadoriaDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Mercadoria mercadoria) {
        boolean status = false;
        try {
            String sql = "INSERT INTO mercadoria (descricao, valor, quantidade, datafabricacao, datavalidade) "
                    + "VALUES ('" + mercadoria.getDescricao() + "', "
                    + mercadoria.getValor() + ", " + mercadoria.getQuantidade() + ", ?, ?);";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setTimestamp(1, Timestamp.valueOf(mercadoria.getDataFabricacao()));
            st.setDate(2, Date.valueOf(mercadoria.getDataValidade()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Mercadoria get(int id) {
        Mercadoria mercadoria = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM mercadoria WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                mercadoria = new Mercadoria(rs.getInt("id"), rs.getString("descricao"), (float) rs.getDouble("valor"),
                        rs.getInt("quantidade"),
                        rs.getTimestamp("datafabricacao").toLocalDateTime(),
                        rs.getDate("datavalidade").toLocalDate());
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return mercadoria;
    }

    public List<Mercadoria> get() {
        return get("");
    }

    public List<Mercadoria> getOrderByID() {
        return get("id");
    }

    public List<Mercadoria> getOrderByDescricao() {
        return get("descricao");
    }

    public List<Mercadoria> getOrderByValor() {
        return get("valor");
    }

    private List<Mercadoria> get(String orderBy) {
        List<Mercadoria> mercadorias = new ArrayList<Mercadoria>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM mercadoria" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Mercadoria p = new Mercadoria(rs.getInt("id"), rs.getString("descricao"), (float) rs.getDouble("valor"),
                        rs.getInt("quantidade"),
                        rs.getTimestamp("datafabricacao").toLocalDateTime(),
                        rs.getDate("datavalidade").toLocalDate());
                mercadorias.add(p);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return mercadorias;
    }

    public boolean update(Mercadoria mercadoria) {
        boolean status = false;
        try {
            String sql = "UPDATE mercadoria SET descricao = '" + mercadoria.getDescricao() + "', "
                    + "valor = " + mercadoria.getValor() + ", "
                    + "quantidade = " + mercadoria.getQuantidade() + ","
                    + "datafabricacao = ?, "
                    + "datavalidade = ? WHERE id = " + mercadoria.getID();
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setTimestamp(1, Timestamp.valueOf(mercadoria.getDataFabricacao()));
            st.setDate(2, Date.valueOf(mercadoria.getDataValidade()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM mercadoria WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
}