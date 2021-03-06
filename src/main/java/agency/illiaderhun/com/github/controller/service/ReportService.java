package agency.illiaderhun.com.github.controller.service;

import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

public interface ReportService {

    Report getReportByOrderId(Integer orderId) throws IdInvalid;
    void createNewReport(String breakingDescription, Integer orderId);
}
